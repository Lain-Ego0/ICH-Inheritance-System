package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.CouseDto;
import com.example.web.dto.CouseStepDto;
import com.example.web.dto.query.CousePagedInput;
import com.example.web.entity.*;
import com.example.web.enums.CouseOrderStatusEnum;
import com.example.web.mapper.*;
import com.example.web.service.CouseService;
import com.example.web.tools.BaseContext;
import com.example.web.tools.Extension;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 课程功能实现类
 */
@Service
public class CouseServiceImpl extends ServiceImpl<CouseMapper, Couse> implements CouseService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的Couse表mapper对象
     */
    @Autowired
    private CouseMapper CouseMapper;
    @Autowired
    private CouseTypeMapper CouseTypeMapper;

    @Autowired
    private CouseStepMapper CouseStepMapper;

    @Autowired
    private CouseStudyScheduleMapper CouseStudyScheduleMapper;

    @Autowired
    private CouseClockMapper CouseClockMapper;

    @Autowired
    private CouseOrderMapper CouseOrderMapper;

    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<Couse> BuilderQuery(CousePagedInput input) {
        // 声明一个支持课程查询的(拉姆达)表达式
        LambdaQueryWrapper<Couse> queryWrapper = Wrappers.<Couse>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, Couse::getId, input.getId())
                .eq(input.getCreatorId() != null, Couse::getCreatorId, input.getCreatorId());
        // 如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getName())) {
            queryWrapper = queryWrapper.like(Couse::getName, input.getName());
        }

        if (input.getCouseTypeId() != null) {
            queryWrapper = queryWrapper.eq(Couse::getCouseTypeId, input.getCouseTypeId());
        }
        if (Extension.isNotNullOrEmpty(input.getContent())) {
            queryWrapper = queryWrapper.like(Couse::getContent, input.getContent());
        }
        if (input.getIsPutaway() != null) {
            queryWrapper = queryWrapper.eq(Couse::getIsPutaway, input.getIsPutaway());
        }
        if (input.getIsFree() != null) {
            queryWrapper = queryWrapper.eq(Couse::getIsFree, input.getIsFree());
        }
        if (Extension.isNotNullOrEmpty(input.getKeyWord())) {
            queryWrapper = queryWrapper.like(Couse::getName, input.getKeyWord())
                    .or().like(Couse::getContent, input.getKeyWord());
        }
        return queryWrapper;
    }

    /**
     * 处理课程对于的外键数据
     */
    private List<CouseDto> DispatchItem(List<CouseDto> items) throws InvocationTargetException, IllegalAccessException {

        for (CouseDto item : items) {
            // 查询创建人
            AppUser CreateUserEntity = AppUserMapper
                    .selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getCreatorId())).stream()
                    .findFirst().orElse(new AppUser());
            item.setCreatorAppUserDto(CreateUserEntity.MapToDto());
            // 查询出关联的CouseType表信息
            CouseType CouseTypeEntity = CouseTypeMapper
                    .selectList(Wrappers.<CouseType>lambdaQuery().eq(CouseType::getId, item.getCouseTypeId())).stream()
                    .findFirst().orElse(new CouseType());
            item.setCouseTypeDto(CouseTypeEntity.MapToDto());

            // 查询对应的课程小节
            List<CouseStep> CouseStepEntities = CouseStepMapper
                    .selectList(Wrappers.<CouseStep>lambdaQuery().eq(CouseStep::getCouseId, item.getId()).orderByAsc(
                            CouseStep::getCreationTime));
            item.setCouseStepDtos(Extension.copyBeanList(CouseStepEntities, CouseStepDto.class));

            // 查询总打卡人次
            Long CouseClockCount = CouseClockMapper
                    .selectCount(Wrappers.<CouseClock>lambdaQuery().eq(CouseClock::getCouseId, item.getId()));
            item.setTotalCouseClockCount(CouseClockCount.intValue());

            // 课程章节数量
            item.setCouseStepCount(CouseStepEntities.size());

            // 查询对应的报名人数
            Long OrderCount = CouseOrderMapper
                    .selectCount(Wrappers.<CouseOrder>lambdaQuery().eq(CouseOrder::getCouseId, item.getId())
                            .eq(CouseOrder::getIsReturn, false));
            item.setEnrollUserCount(OrderCount.intValue());

            if (BaseContext.getCurrentUserDto() != null && BaseContext.getCurrentUserDto().getUserId() != null) {

                CouseOrder CouseOrderEntity = CouseOrderMapper
                        .selectList(Wrappers.<CouseOrder>lambdaQuery().eq(CouseOrder::getCouseId, item.getId())
                                .eq(CouseOrder::getUserId, BaseContext.getCurrentUserDto().getUserId())
                                .eq(CouseOrder::getIsReturn, false)
                                .eq(CouseOrder::getOrderStatus, CouseOrderStatusEnum.已支付.index()))
                        .stream().findFirst().orElse(new CouseOrder());
                item.setIsEnroll(CouseOrderEntity.getId() != null);
            }

        }
        return items;
    }

    /**
     * 课程分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<CouseDto> List(CousePagedInput input) {
        // 构建where条件+排序
        LambdaQueryWrapper<Couse> queryWrapper = BuilderQuery(input);

        // 按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(Couse::getCreationTime);
        // 构建一个分页查询的model
        Page<Couse> page = new Page<>(input.getPage(), input.getLimit());
        // 从数据库进行分页查询获取课程数据
        IPage<Couse> pageRecords = CouseMapper.selectPage(page, queryWrapper);
        // 获取所有满足条件的数据行数
        Long totalCount = CouseMapper.selectCount(queryWrapper);
        // 把Couse实体转换成Couse传输模型
        List<CouseDto> items = Extension.copyBeanList(pageRecords.getRecords(), CouseDto.class);
        // 计算表的数据
        items = DispatchItem(items);

        // 返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个课程查询
     */
    @SneakyThrows
    @Override
    public CouseDto Get(CousePagedInput input) {
        if (input.getId() == null) {
            return new CouseDto();
        }
        PagedResult<CouseDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new CouseDto());
    }

    /**
     * 课程创建或者修改
     */
    @SneakyThrows
    @Override
    public CouseDto CreateOrEdit(CouseDto input) {
        // 声明一个课程实体
        Couse Couse = input.MapToEntity();
        // 调用数据库的增加或者修改方法
        saveOrUpdate(Couse);
        // 把传输模型返回给前端
        return Couse.MapToDto();
    }

    /**
     * 课程删除
     */
    @Override
    public void Delete(IdInput input) {
        Couse entity = CouseMapper.selectById(input.getId());
        CouseMapper.deleteById(entity);
    }

    /**
     * 课程批量删除
     */
    @Override
    public void BatchDelete(IdsInput input) {
        for (Integer id : input.getIds()) {
            IdInput idInput = new IdInput();
            idInput.setId(id);
            Delete(idInput);
        }
    }

    /**
     * 推荐用户可能喜欢的课程 协同过滤+内容推荐
     */
    @SneakyThrows
    @Override
    public PagedResult<CouseDto> Recommend(CouseDto input) {
        Integer userId = BaseContext.getCurrentUserDto().getUserId();

        // 1. 获取用户历史学习的课程
        List<CouseOrder> userOrders = CouseOrderMapper.selectList(
                Wrappers.<CouseOrder>lambdaQuery()
                        .eq(CouseOrder::getUserId, userId)
                        .eq(CouseOrder::getIsReturn, false));

        // 2. 获取相似用户的课程选择
        List<Integer> similarUserCourses = new ArrayList<>();
        if (!userOrders.isEmpty()) {
            // 找到学习相同课程的用户
            List<Integer> similarUserIds = CouseOrderMapper.selectList(
                    Wrappers.<CouseOrder>lambdaQuery()
                            .in(CouseOrder::getCouseId,
                                    userOrders.stream().map(CouseOrder::getCouseId).collect(Collectors.toList()))
                            .ne(CouseOrder::getUserId, userId)
                            .eq(CouseOrder::getIsReturn, false))
                    .stream().map(CouseOrder::getUserId).distinct().collect(Collectors.toList());

            // 获取相似用户学习的其他课程
            if (!similarUserIds.isEmpty()) {
                similarUserCourses = CouseOrderMapper.selectList(
                        Wrappers.<CouseOrder>lambdaQuery()
                                .in(CouseOrder::getUserId, similarUserIds)
                                .eq(CouseOrder::getIsReturn, false))
                        .stream().map(CouseOrder::getCouseId).distinct().collect(Collectors.toList());
            }
        }

        // 3. 构建推荐查询条件
        LambdaQueryWrapper<Couse> queryWrapper = Wrappers.<Couse>lambdaQuery()
                .eq(Couse::getIsPutaway, true) // 只推荐上架的课程
                .notIn(!userOrders.isEmpty(),
                        Couse::getId,
                        userOrders.stream().map(CouseOrder::getCouseId).collect(Collectors.toList()));

        // 如果有相似用户的课程选择，优先推荐这些课程
        if (!similarUserCourses.isEmpty()) {
            final List<Integer> finalSimilarUserCourses = similarUserCourses;
            queryWrapper.and(wrapper -> wrapper
                    .in(Couse::getId, finalSimilarUserCourses)
                    .or()
                    .in(!userOrders.isEmpty(),
                            Couse::getCouseTypeId,
                            userOrders.stream()
                                    .map(order -> CouseMapper.selectById(order.getCouseId()).getCouseTypeId())
                                    .distinct()
                                    .collect(Collectors.toList())));
        }

        // 4. 分页查询推荐课程
        Page<Couse> page = new Page<>(1, 10);
        IPage<Couse> pageRecords = CouseMapper.selectPage(page, queryWrapper);
        List<Couse> courses = pageRecords.getRecords();
        // 转换成dto
        List<CouseDto> coursesDtos = Extension.copyBeanList(courses, CouseDto.class);
        coursesDtos = DispatchItem(coursesDtos);
        final List<Integer> finalSimilarUserCourses = similarUserCourses;
        // 5. 获取每个课程的报名人数热度并排序
        List<CouseDto> sortedCourses = coursesDtos.stream()
                .map(course -> {
                    // 查询课程报名人数
                    Long enrollCount = CouseOrderMapper.selectCount(
                            Wrappers.<CouseOrder>lambdaQuery()
                                    .eq(CouseOrder::getCouseId, course.getId())
                                    .eq(CouseOrder::getIsReturn, false));
                    course.setEnrollUserCount(enrollCount.intValue());
                    return course;
                })
                .sorted((c1, c2) -> {
                    // 优先按是否是相似用户的课程排序，其次按报名人数排序
                    boolean c1InSimilar = finalSimilarUserCourses.contains(c1.getId());
                    boolean c2InSimilar = finalSimilarUserCourses.contains(c2.getId());
                    if (c1InSimilar != c2InSimilar) {
                        return c1InSimilar ? -1 : 1;
                    }
                    return c2.getEnrollUserCount().compareTo(c1.getEnrollUserCount());
                })
                .collect(Collectors.toList());

        return PagedResult.GetInstance(sortedCourses, pageRecords.getTotal());
    }

    /**
     * 统计课程报名人数
     */
    @SneakyThrows
    @Override
    public List<Object> CouseEnrollCountEchart() {
        List<Object> list = new ArrayList<>();
        // 查询课程
        List<Couse> couses = CouseMapper.selectList(Wrappers.<Couse>lambdaQuery());
        for (Couse couse : couses) {
            Map<String, Object> map = new HashMap<>();

            // 查询对应的报名人数
            Long OrderCount = CouseOrderMapper
                    .selectCount(Wrappers.<CouseOrder>lambdaQuery().eq(CouseOrder::getCouseId, couse.getId())
                            .eq(CouseOrder::getIsReturn, false));
            map.put("name", couse.getName());

            map.put("value", OrderCount);
            list.add(map);
        }
        return list;
    }
}
