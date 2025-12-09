package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.CouseStudyScheduleDto;
import com.example.web.dto.query.CouseStudySchedulePagedInput;
import com.example.web.entity.*;
import com.example.web.mapper.*;
import com.example.web.service.CouseStudyScheduleService;
import com.example.web.tools.Extension;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 课程学习进度功能实现类
 */
@Service
public class CouseStudyScheduleServiceImpl extends ServiceImpl<CouseStudyScheduleMapper, CouseStudySchedule>
        implements CouseStudyScheduleService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的CouseStudySchedule表mapper对象
     */
    @Autowired
    private CouseStudyScheduleMapper CouseStudyScheduleMapper;
    @Autowired
    private CouseMapper CouseMapper;
    @Autowired
    private CouseStepMapper CouseStepMapper;

    @Autowired
    private CouseClockMapper CouseClockMapper;

    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<CouseStudySchedule> BuilderQuery(CouseStudySchedulePagedInput input) {
        // 声明一个支持课程学习进度查询的(拉姆达)表达式
        LambdaQueryWrapper<CouseStudySchedule> queryWrapper = Wrappers.<CouseStudySchedule>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, CouseStudySchedule::getId, input.getId())
                .eq(input.getCreatorId() != null, CouseStudySchedule::getCreatorId, input.getCreatorId());
        // 如果前端搜索传入查询条件则拼接查询条件

        if (input.getStudyUserId() != null) {
            queryWrapper = queryWrapper.eq(CouseStudySchedule::getStudyUserId, input.getStudyUserId());
        }

        if (input.getCouseStepId() != null) {
            queryWrapper = queryWrapper.eq(CouseStudySchedule::getCouseStepId, input.getCouseStepId());
        }

        if (input.getCouseId() != null) {
            queryWrapper = queryWrapper.eq(CouseStudySchedule::getCouseId, input.getCouseId());
        }
        if (input.getBeginTimeRange() != null && !input.getBeginTimeRange().isEmpty()) {
            queryWrapper = queryWrapper.lt(CouseStudySchedule::getBeginTime, input.getBeginTimeRange().get(1));
            queryWrapper = queryWrapper.gt(CouseStudySchedule::getBeginTime, input.getBeginTimeRange().get(0));
        }
        if (input.getLastUploadTimeRange() != null && !input.getLastUploadTimeRange().isEmpty()) {
            queryWrapper = queryWrapper.lt(CouseStudySchedule::getLastUploadTime,
                    input.getLastUploadTimeRange().get(1));
            queryWrapper = queryWrapper.gt(CouseStudySchedule::getLastUploadTime,
                    input.getLastUploadTimeRange().get(0));
        }

        if (input.getTeachUserId() != null) {
            // 查询对应的课程
            List<Couse> couseList = CouseMapper
                    .selectList(Wrappers.<Couse>lambdaQuery().eq(Couse::getCreatorId, input.getTeachUserId()));
            if (couseList.size() > 0) {
                queryWrapper = queryWrapper.in(CouseStudySchedule::getCouseId,
                        couseList.stream().map(Couse::getId).collect(Collectors.toList()));
            } else {
                queryWrapper = queryWrapper.eq(CouseStudySchedule::getCouseId, 0);
            }
        }

        return queryWrapper;
    }

    /**
     * 处理课程学习进度对于的外键数据
     */
    private List<CouseStudyScheduleDto> DispatchItem(List<CouseStudyScheduleDto> items)
            throws InvocationTargetException, IllegalAccessException {

        for (CouseStudyScheduleDto item : items) {

            // 查询出关联的AppUser表信息
            AppUser StudyUserEntity = AppUserMapper
                    .selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getStudyUserId())).stream()
                    .findFirst().orElse(new AppUser());
            item.setStudyUserDto(StudyUserEntity.MapToDto());

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
        }
        return items;
    }

    /**
     * 课程学习进度分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<CouseStudyScheduleDto> List(CouseStudySchedulePagedInput input) {
        // 构建where条件+排序
        LambdaQueryWrapper<CouseStudySchedule> queryWrapper = BuilderQuery(input);

        // 按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(CouseStudySchedule::getCreationTime);
        // 构建一个分页查询的model
        Page<CouseStudySchedule> page = new Page<>(input.getPage(), input.getLimit());
        // 从数据库进行分页查询获取课程学习进度数据
        IPage<CouseStudySchedule> pageRecords = CouseStudyScheduleMapper.selectPage(page, queryWrapper);
        // 获取所有满足条件的数据行数
        Long totalCount = CouseStudyScheduleMapper.selectCount(queryWrapper);
        // 把CouseStudySchedule实体转换成CouseStudySchedule传输模型
        List<CouseStudyScheduleDto> items = Extension.copyBeanList(pageRecords.getRecords(),
                CouseStudyScheduleDto.class);
        // 计算表的数据
        items = DispatchItem(items);

        // 返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个课程学习进度查询
     */
    @SneakyThrows
    @Override
    public CouseStudyScheduleDto Get(CouseStudySchedulePagedInput input) {
        if (input.getId() == null) {
            return new CouseStudyScheduleDto();
        }
        PagedResult<CouseStudyScheduleDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new CouseStudyScheduleDto());
    }

    /**
     * 课程学习进度创建或者修改
     */
    @SneakyThrows
    @Override
    public CouseStudyScheduleDto CreateOrEdit(CouseStudyScheduleDto input) {
        // 声明一个课程学习进度实体
        CouseStudySchedule CouseStudySchedule = input.MapToEntity();
        // 调用数据库的增加或者修改方法
        saveOrUpdate(CouseStudySchedule);
        // 把传输模型返回给前端
        return CouseStudySchedule.MapToDto();
    }

    /**
     * 课程学习进度删除
     */
    @Override
    public void Delete(IdInput input) {
        CouseStudySchedule entity = CouseStudyScheduleMapper.selectById(input.getId());
        CouseStudyScheduleMapper.deleteById(entity);
    }

    /**
     * 课程学习进度批量删除
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
     * 更新时长
     */
    @SneakyThrows
    @Override
    public CouseStudyScheduleDto UpdateTotalDurtion(CouseStudyScheduleDto input) {
        CouseStudySchedule entity = CouseStudyScheduleMapper.selectById(input.getId());
        if (entity != null) {
            entity.setDurtion(entity.getDurtion() + 1000);
            CouseStudyScheduleMapper.updateById(entity);

            CouseStep couseStep = CouseStepMapper.selectById(entity.getCouseStepId());

            // 查询是否打卡过
            Long isClock = CouseClockMapper.selectCount(
                    Wrappers.<CouseClock>lambdaQuery().eq(CouseClock::getCouseStepId, entity.getCouseStepId())
                            .eq(CouseClock::getUserId, entity.getStudyUserId()));
            if (isClock.intValue() == 0) {
                // 如果上传的时长是小节时长的60%，则标记为打卡
                if (entity.getDurtion() >= couseStep.getDurtion() * 0.6) {
                    CouseClock couseClock = new CouseClock();
                    couseClock.setCouseStepId(entity.getCouseStepId());
                    couseClock.setUserId(entity.getStudyUserId());
                    couseClock.setClockTime(LocalDateTime.now());
                    couseClock.setCouseId(couseStep.getCouseId());
                    CouseClockMapper.insert(couseClock);

                    // 查询课程
                    Couse couse = CouseMapper.selectById(couseStep.getCouseId());

                }
            }

            return entity.MapToDto();
        } else {
            entity = input.MapToEntity();
            CouseStudyScheduleMapper.insert(entity);
            return entity.MapToDto();
        }

    }
}
