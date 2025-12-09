package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.CouseClockDto;
import com.example.web.dto.query.CouseClockPagedInput;
import com.example.web.entity.AppUser;
import com.example.web.entity.Couse;
import com.example.web.entity.CouseClock;
import com.example.web.entity.CouseStep;
import com.example.web.mapper.AppUserMapper;
import com.example.web.mapper.CouseClockMapper;
import com.example.web.mapper.CouseMapper;
import com.example.web.mapper.CouseStepMapper;
import com.example.web.service.CouseClockService;
import com.example.web.tools.Extension;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 课程打卡功能实现类
 */
@Service
public class CouseClockServiceImpl extends ServiceImpl<CouseClockMapper, CouseClock> implements CouseClockService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的CouseClock表mapper对象
     */
    @Autowired
    private CouseClockMapper CouseClockMapper;
    @Autowired
    private CouseMapper CouseMapper;
    @Autowired
    private CouseStepMapper CouseStepMapper;

    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<CouseClock> BuilderQuery(CouseClockPagedInput input) {
        // 声明一个支持课程打卡查询的(拉姆达)表达式
        LambdaQueryWrapper<CouseClock> queryWrapper = Wrappers.<CouseClock>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, CouseClock::getId, input.getId())
                .eq(input.getCreatorId() != null, CouseClock::getCreatorId, input.getCreatorId());
        // 如果前端搜索传入查询条件则拼接查询条件

        if (input.getCouseId() != null) {
            queryWrapper = queryWrapper.eq(CouseClock::getCouseId, input.getCouseId());
        }

        if (input.getCouseStepId() != null) {
            queryWrapper = queryWrapper.eq(CouseClock::getCouseStepId, input.getCouseStepId());
        }

        if (input.getUserId() != null) {
            queryWrapper = queryWrapper.eq(CouseClock::getUserId, input.getUserId());
        }
        if (input.getClockTimeRange() != null && !input.getClockTimeRange().isEmpty()) {
            queryWrapper = queryWrapper.lt(CouseClock::getClockTime, input.getClockTimeRange().get(1));
            queryWrapper = queryWrapper.gt(CouseClock::getClockTime, input.getClockTimeRange().get(0));
        }

        if (input.getTeachUserId() != null) {
            // 查询对应的课程
            List<Couse> couseList = CouseMapper
                    .selectList(Wrappers.<Couse>lambdaQuery().eq(Couse::getCreatorId, input.getTeachUserId()));
            if (couseList.size() > 0) {
                queryWrapper = queryWrapper.in(CouseClock::getCouseId,
                        couseList.stream().map(Couse::getId).collect(Collectors.toList()));
            } else {
                queryWrapper = queryWrapper.eq(CouseClock::getCouseId, 0);
            }
        }
        return queryWrapper;
    }

    /**
     * 处理课程打卡对于的外键数据
     */
    private List<CouseClockDto> DispatchItem(List<CouseClockDto> items)
            throws InvocationTargetException, IllegalAccessException {

        for (CouseClockDto item : items) {

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

            // 查询出关联的CouseStep表信息
            CouseStep CouseStepEntity = CouseStepMapper
                    .selectList(Wrappers.<CouseStep>lambdaQuery().eq(CouseStep::getId, item.getCouseStepId())).stream()
                    .findFirst().orElse(new CouseStep());
            item.setCouseStepDto(CouseStepEntity.MapToDto());

            // 查询出关联的AppUser表信息
            AppUser UserEntity = AppUserMapper
                    .selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getUserId())).stream()
                    .findFirst().orElse(new AppUser());
            item.setUserDto(UserEntity.MapToDto());
        }
        return items;
    }

    /**
     * 课程打卡分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<CouseClockDto> List(CouseClockPagedInput input) {
        // 构建where条件+排序
        LambdaQueryWrapper<CouseClock> queryWrapper = BuilderQuery(input);

        // 按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(CouseClock::getCreationTime);
        // 构建一个分页查询的model
        Page<CouseClock> page = new Page<>(input.getPage(), input.getLimit());
        // 从数据库进行分页查询获取课程打卡数据
        IPage<CouseClock> pageRecords = CouseClockMapper.selectPage(page, queryWrapper);
        // 获取所有满足条件的数据行数
        Long totalCount = CouseClockMapper.selectCount(queryWrapper);
        // 把CouseClock实体转换成CouseClock传输模型
        List<CouseClockDto> items = Extension.copyBeanList(pageRecords.getRecords(), CouseClockDto.class);
        // 计算表的数据
        items = DispatchItem(items);

        // 返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个课程打卡查询
     */
    @SneakyThrows
    @Override
    public CouseClockDto Get(CouseClockPagedInput input) {
        if (input.getId() == null) {
            return new CouseClockDto();
        }
        PagedResult<CouseClockDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new CouseClockDto());
    }

    /**
     * 课程打卡创建或者修改
     */
    @SneakyThrows
    @Override
    public CouseClockDto CreateOrEdit(CouseClockDto input) {
        // 声明一个课程打卡实体
        CouseClock CouseClock = input.MapToEntity();
        // 调用数据库的增加或者修改方法
        saveOrUpdate(CouseClock);
        // 把传输模型返回给前端
        return CouseClock.MapToDto();
    }

    /**
     * 课程打卡删除
     */
    @Override
    public void Delete(IdInput input) {
        CouseClock entity = CouseClockMapper.selectById(input.getId());
        CouseClockMapper.deleteById(entity);
    }

    /**
     * 课程打卡批量删除
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
