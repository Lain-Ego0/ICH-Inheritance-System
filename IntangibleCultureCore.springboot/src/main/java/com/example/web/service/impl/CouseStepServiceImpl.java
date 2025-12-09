package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.CouseStepDto;
import com.example.web.dto.query.CouseStepPagedInput;
import com.example.web.entity.*;
import com.example.web.mapper.*;
import com.example.web.service.CouseStepService;
import com.example.web.tools.BaseContext;
import com.example.web.tools.Extension;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 课程章节功能实现类
 */
@Service
public class CouseStepServiceImpl extends ServiceImpl<CouseStepMapper, CouseStep> implements CouseStepService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的CouseStep表mapper对象
     */
    @Autowired
    private CouseStepMapper CouseStepMapper;
    @Autowired
    private CouseMapper CouseMapper;

    @Autowired
    private CouseClockMapper CouseClockMapper;

    @Autowired
    private CouseStudyScheduleMapper CouseStudyScheduleMapper;

    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<CouseStep> BuilderQuery(CouseStepPagedInput input) {
        // 声明一个支持课程章节查询的(拉姆达)表达式
        LambdaQueryWrapper<CouseStep> queryWrapper = Wrappers.<CouseStep>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, CouseStep::getId, input.getId())
                .eq(input.getCreatorId() != null, CouseStep::getCreatorId, input.getCreatorId());
        // 如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getName())) {
            queryWrapper = queryWrapper.like(CouseStep::getName, input.getName());
        }

        if (input.getCouseId() != null) {
            queryWrapper = queryWrapper.eq(CouseStep::getCouseId, input.getCouseId());
        }
        return queryWrapper;
    }

    /**
     * 处理课程章节对于的外键数据
     */
    private List<CouseStepDto> DispatchItem(List<CouseStepDto> items)
            throws InvocationTargetException, IllegalAccessException {

        for (CouseStepDto item : items) {
            // 查询创建人
            AppUser CreateUserEntity = AppUserMapper
                    .selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getCreatorId())).stream()
                    .findFirst().orElse(new AppUser());
            item.setCreatorAppUserDto(CreateUserEntity.MapToDto());
            // 查询出关联的Couse表信息
            Couse CouseEntity = CouseMapper
                    .selectList(Wrappers.<Couse>lambdaQuery().eq(Couse::getId, item.getCouseId())).stream().findFirst()
                    .orElse(new Couse());
            item.setCouseDto(CouseEntity.MapToDto());

            List<CouseClock> CouseClockList = CouseClockMapper
                    .selectList(Wrappers.<CouseClock>lambdaQuery().eq(CouseClock::getCouseStepId, item.getId()));

            item.setCouseClockCount(CouseClockList.size());

            if (BaseContext.getCurrentUserDto() != null && BaseContext.getCurrentUserDto().getUserId() != null) {
                // 查询出当前用户的学习进度
                List<CouseStudySchedule> CouseStudyScheduleList = CouseStudyScheduleMapper
                        .selectList(Wrappers.<CouseStudySchedule>lambdaQuery()
                                .eq(CouseStudySchedule::getCouseId, item.getCouseId())
                                .eq(CouseStudySchedule::getCouseStepId, item.getId())
                                .eq(CouseStudySchedule::getStudyUserId, BaseContext.getCurrentUserDto().getUserId()));
                if (CouseStudyScheduleList.size() > 0) {
                    item.setStudyTotalDurtion(
                            CouseStudyScheduleList.stream().mapToDouble(CouseStudySchedule::getDurtion).sum());

                }

                Integer isClock = (int) CouseClockList.stream()

                        .filter(x -> x.getUserId().equals(BaseContext.getCurrentUserDto().getUserId()))
                        .count();
                item.setIsClock(isClock > 0);

            }
        }
        return items;
    }

    /**
     * 课程章节分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<CouseStepDto> List(CouseStepPagedInput input) {
        // 构建where条件+排序
        LambdaQueryWrapper<CouseStep> queryWrapper = BuilderQuery(input);

        // 按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByAsc(CouseStep::getCreationTime);
        // 构建一个分页查询的model
        Page<CouseStep> page = new Page<>(input.getPage(), input.getLimit());
        // 从数据库进行分页查询获取课程章节数据
        IPage<CouseStep> pageRecords = CouseStepMapper.selectPage(page, queryWrapper);
        // 获取所有满足条件的数据行数
        Long totalCount = CouseStepMapper.selectCount(queryWrapper);
        // 把CouseStep实体转换成CouseStep传输模型
        List<CouseStepDto> items = Extension.copyBeanList(pageRecords.getRecords(), CouseStepDto.class);
        // 计算表的数据
        items = DispatchItem(items);

        // 返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个课程章节查询
     */
    @SneakyThrows
    @Override
    public CouseStepDto Get(CouseStepPagedInput input) {
        if (input.getId() == null) {
            return new CouseStepDto();
        }
        PagedResult<CouseStepDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new CouseStepDto());
    }

    /**
     * 课程章节创建或者修改
     */
    @SneakyThrows
    @Override
    public CouseStepDto CreateOrEdit(CouseStepDto input) {
        // 声明一个课程章节实体
        CouseStep CouseStep = input.MapToEntity();
        // 调用数据库的增加或者修改方法
        saveOrUpdate(CouseStep);
        // 把传输模型返回给前端
        return CouseStep.MapToDto();
    }

    /**
     * 课程章节删除
     */
    @Override
    public void Delete(IdInput input) {
        CouseStep entity = CouseStepMapper.selectById(input.getId());
        CouseStepMapper.deleteById(entity);
    }

    /**
     * 课程章节批量删除
     */
    @Override
    public void BatchDelete(IdsInput input) {
        for (Integer id : input.getIds()) {
            IdInput idInput = new IdInput();
            idInput.setId(id);
            Delete(idInput);
        }
    }

}
