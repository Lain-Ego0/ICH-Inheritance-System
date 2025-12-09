package com.example.web.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.ActivityDto;
import com.example.web.dto.AppUserDto;
import com.example.web.dto.query.ActivityPagedInput;
import com.example.web.entity.Activity;
import com.example.web.entity.ActivityRecord;
import com.example.web.entity.AppUser;
import com.example.web.enums.ActivityAuditStatusEnum;
import com.example.web.enums.ActivityStatusEnum;
import com.example.web.mapper.ActivityMapper;
import com.example.web.mapper.ActivityRecordMapper;
import com.example.web.mapper.AppUserMapper;
import com.example.web.service.ActivityService;
import com.example.web.tools.BaseContext;
import com.example.web.tools.Extension;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import com.example.web.tools.dto.SelectResult;
import com.example.web.tools.exception.CustomException;

import lombok.SneakyThrows;

/**
 * 活动功能实现类
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的Activity表mapper对象
     */
    @Autowired
    private ActivityMapper ActivityMapper;

    @Autowired
    private ActivityRecordMapper ActivityRecordMapper;

    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<Activity> BuilderQuery(ActivityPagedInput input) {
        // 声明一个支持活动查询的(拉姆达)表达式
        LambdaQueryWrapper<Activity> queryWrapper = Wrappers.<Activity>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, Activity::getId, input.getId())
                .eq(input.getCreatorId() != null, Activity::getCreatorId, input.getCreatorId());
        // 如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getTitle())) {
            queryWrapper = queryWrapper.like(Activity::getTitle, input.getTitle());
        }
        if (Extension.isNotNullOrEmpty(input.getActivityType())) {
            queryWrapper = queryWrapper.like(Activity::getActivityType, input.getActivityType());
        }
        if (Extension.isNotNullOrEmpty(input.getLocation())) {
            queryWrapper = queryWrapper.like(Activity::getLocation, input.getLocation());
        }

        if (input.getUserId() != null) {
            queryWrapper = queryWrapper.eq(Activity::getUserId, input.getUserId());
        }

        if (input.getActivityStatus() != null) {
            queryWrapper = queryWrapper.eq(Activity::getActivityStatus, input.getActivityStatus());
        }
        if (input.getActiveTimeRange() != null && !input.getActiveTimeRange().isEmpty()) {
            queryWrapper = queryWrapper.ge(Activity::getBeginActiveTime, input.getActiveTimeRange().get(0));
            queryWrapper = queryWrapper.le(Activity::getEndActiveTime, input.getActiveTimeRange().get(1));
        }
        if (Extension.isNotNullOrEmpty(input.getContent())) {
            queryWrapper = queryWrapper.like(Activity::getContent, input.getContent());
        }
        if (input.getJoinTimeRange() != null && !input.getJoinTimeRange().isEmpty()) {
            queryWrapper = queryWrapper.ge(Activity::getBeginJoinTime, input.getJoinTimeRange().get(0));
            queryWrapper = queryWrapper.le(Activity::getEndJoinTime, input.getJoinTimeRange().get(1));
        }
        return queryWrapper;
    }

    /**
     * 处理活动对于的外键数据
     */
    private List<ActivityDto> DispatchItem(List<ActivityDto> items)
            throws InvocationTargetException, IllegalAccessException {

        for (ActivityDto item : items) {

            // 查询出关联的AppUser表信息
            AppUser UserEntity = AppUserMapper.selectById(item.getUserId());
            item.setUserDto(UserEntity != null ? UserEntity.MapToDto() : new AppUserDto());

            Integer userId = BaseContext.getCurrentUserDto().getUserId();
            if (userId != null && userId > 0) {
                List<ActivityRecord> myActivityRecords = ActivityRecordMapper.selectList(
                        Wrappers.<ActivityRecord>lambdaQuery().eq(ActivityRecord::getJoinUserId, userId)
                                .eq(ActivityRecord::getIsCancel, false)
                                .in(ActivityRecord::getActivityAuditStatus,
                                        ActivityAuditStatusEnum.待审核.index(),
                                        ActivityAuditStatusEnum.审核通过.index())
                                .eq(ActivityRecord::getActivityId, item.getId()));

                if (myActivityRecords.size() > 0) {
                    item.setIsJoin(true);
                } else {
                    item.setIsJoin(false);
                }

            }

            // 查看报名的人数
            long count = ActivityRecordMapper.selectCount(
                    Wrappers.<ActivityRecord>lambdaQuery()
                            .eq(ActivityRecord::getIsCancel, false)
                            .in(ActivityRecord::getActivityAuditStatus,
                                    ActivityAuditStatusEnum.待审核.index(),
                                    ActivityAuditStatusEnum.审核通过.index())
                            .eq(ActivityRecord::getActivityId, item.getId()));

            item.setJoinCount(count);

        }

        return items;
    }

    /**
     * 活动分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<ActivityDto> List(ActivityPagedInput input) {
        // 构建where条件+排序
        LambdaQueryWrapper<Activity> queryWrapper = BuilderQuery(input);

        // 按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(Activity::getCreationTime);
        // 构建一个分页查询的model
        Page<Activity> page = new Page<>(input.getPage(), input.getLimit());
        // 从数据库进行分页查询获取活动数据
        IPage<Activity> pageRecords = ActivityMapper.selectPage(page, queryWrapper);
        // 获取所有满足条件的数据行数
        Long totalCount = ActivityMapper.selectCount(queryWrapper);
        // 把Activity实体转换成Activity传输模型
        List<ActivityDto> items = Extension.copyBeanList(pageRecords.getRecords(), ActivityDto.class);

        DispatchItem(items);
        // 返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 查询活动分类
     */
    @Override
    public PagedResult<SelectResult> GetActivityType() {
        List<Activity> activityTypes = ActivityMapper.selectList(Wrappers.<Activity>lambdaQuery());
        // 拿出type进行去重
        List<String> types = activityTypes.stream().map(Activity::getActivityType).distinct().toList();
        List<SelectResult> selectResults = types.stream().map(n -> new SelectResult(n, n, n, "")).toList();
        return PagedResult.GetInstance(selectResults, (long) selectResults.size());
    }

    /**
     * 单个活动查询
     */
    @SneakyThrows
    @Override
    public ActivityDto Get(ActivityPagedInput input) {
        if (input.getId() == null) {
            return new ActivityDto();
        }

        PagedResult<ActivityDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new ActivityDto());
    }

    /**
     * 活动创建或者修改
     */
    @SneakyThrows
    @Override
    public ActivityDto CreateOrEdit(ActivityDto input) {

        if (input.getBeginJoinTime().isBefore(LocalDateTime.now())) {
            throw new CustomException("活动报名开始时间不能小于当前时间");
        }
        if (input.getEndJoinTime().isAfter(input.getBeginActiveTime())) {
            throw new CustomException("活动报名时间不能大于活动开始时间");
        }

        // 声明一个活动实体
        Activity Activity = input.MapToEntity();
        // 调用数据库的增加或者修改方法
        saveOrUpdate(Activity);
        // 把传输模型返回给前端
        return Activity.MapToDto();
    }

    /**
     * 活动删除
     */
    @Override
    public void Delete(IdInput input) {
        Activity entity = ActivityMapper.selectById(input.getId());
        ActivityMapper.deleteById(entity);
    }

    /**
     * 活动批量删除
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
     * 自动更新活动状态
     */
    @Override
    public void AutomaticallyActivityChangeStatus() {
        // 查询所有没有完成的活动
        List<Activity> activities = ActivityMapper.selectList(
                Wrappers.<Activity>lambdaQuery().notIn(Activity::getActivityStatus,
                        ActivityStatusEnum.活动结束.index(),
                        ActivityStatusEnum.活动取消.index()));
        for (Activity activity : activities) {
            if (activity.getActivityStatus() == ActivityStatusEnum.待进行.index()
                    && activity.getBeginJoinTime().isBefore(LocalDateTime.now())
                    && activity.getEndJoinTime().isAfter(LocalDateTime.now())) {
                activity.setActivityStatus(ActivityStatusEnum.报名中.index());
            }
            if (activity.getActivityStatus() == ActivityStatusEnum.报名中.index()
                    && activity.getEndJoinTime().isBefore(LocalDateTime.now())) {
                activity.setActivityStatus(ActivityStatusEnum.报名结束.index());
            }
            if (activity.getActivityStatus() == ActivityStatusEnum.活动进行中.index()
                    && activity.getBeginActiveTime().isBefore(LocalDateTime.now())
                    && activity.getEndActiveTime().isAfter(LocalDateTime.now())) {
                activity.setActivityStatus(ActivityStatusEnum.活动进行中.index());
            }
            if (activity.getActivityStatus() == ActivityStatusEnum.活动进行中.index()
                    && activity.getEndActiveTime().isBefore(LocalDateTime.now())) {
                activity.setActivityStatus(ActivityStatusEnum.活动结束.index());
            }
            saveOrUpdate(activity);
        }
    }
}
