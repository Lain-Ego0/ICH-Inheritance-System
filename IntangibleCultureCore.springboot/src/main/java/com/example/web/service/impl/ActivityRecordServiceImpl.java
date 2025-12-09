package com.example.web.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.ActivityDto;
import com.example.web.dto.ActivityRecordDto;
import com.example.web.dto.AppUserDto;
import com.example.web.dto.query.ActivityRecordPagedInput;
import com.example.web.entity.Activity;
import com.example.web.entity.ActivityRecord;
import com.example.web.entity.AppUser;

import com.example.web.enums.ActivityAuditStatusEnum;
import com.example.web.mapper.ActivityMapper;
import com.example.web.mapper.ActivityRecordMapper;
import com.example.web.mapper.AppUserMapper;

import com.example.web.service.ActivityRecordService;
import com.example.web.tools.EmailUtil;
import com.example.web.tools.Extension;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import com.example.web.tools.exception.CustomException;

import lombok.SneakyThrows;

/**
 * 活动登记功能实现类
 */
@Service
public class ActivityRecordServiceImpl extends ServiceImpl<ActivityRecordMapper, ActivityRecord>
        implements ActivityRecordService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的ActivityRecord表mapper对象
     */
    @Autowired
    private ActivityRecordMapper ActivityRecordMapper;
    @Autowired
    private ActivityMapper ActivityMapper;


    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<ActivityRecord> BuilderQuery(ActivityRecordPagedInput input) {
        // 声明一个支持活动登记查询的(拉姆达)表达式
        LambdaQueryWrapper<ActivityRecord> queryWrapper = Wrappers.<ActivityRecord>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, ActivityRecord::getId, input.getId())
                .eq(input.getCreatorId() != null, ActivityRecord::getCreatorId, input.getCreatorId());
        // 如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getAuditResult())) {
            queryWrapper = queryWrapper.like(ActivityRecord::getAuditResult, input.getAuditResult());
        }
        if (Extension.isNotNullOrEmpty(input.getComment())) {
            queryWrapper = queryWrapper.like(ActivityRecord::getComment, input.getComment());
        }

        if (input.getActivityId() != null) {
            queryWrapper = queryWrapper.eq(ActivityRecord::getActivityId, input.getActivityId());
        }

        if (input.getJoinUserId() != null) {
            queryWrapper = queryWrapper.eq(ActivityRecord::getJoinUserId, input.getJoinUserId());
        }

        if (input.getActivityAuditStatus() != null) {
            queryWrapper = queryWrapper.eq(ActivityRecord::getActivityAuditStatus, input.getActivityAuditStatus());
        }
        if (input.getCancelTimeRange() != null && !input.getCancelTimeRange().isEmpty()) {
            queryWrapper = queryWrapper.lt(ActivityRecord::getCancelTime, input.getCancelTimeRange().get(1));
            queryWrapper = queryWrapper.gt(ActivityRecord::getCancelTime, input.getCancelTimeRange().get(0));
        }
        if (input.getCheckTimeRange() != null && !input.getCheckTimeRange().isEmpty()) {
            queryWrapper = queryWrapper.lt(ActivityRecord::getCheckTime, input.getCheckTimeRange().get(1));
            queryWrapper = queryWrapper.gt(ActivityRecord::getCheckTime, input.getCheckTimeRange().get(0));
        }
        if (input.getIsCancel() != null) {
            queryWrapper = queryWrapper.eq(ActivityRecord::getIsCancel, input.getIsCancel());
        }
        if (input.getIsCheck() != null) {
            queryWrapper = queryWrapper.eq(ActivityRecord::getIsCheck, input.getIsCheck());
        }
        if (input.getCheckCode() != null) {
            queryWrapper = queryWrapper.eq(ActivityRecord::getCheckCode, input.getCheckCode());
        }

        return queryWrapper;
    }

    /**
     * 处理活动登记对于的外键数据
     */
    private List<ActivityRecordDto> DispatchItem(List<ActivityRecordDto> items)
            throws InvocationTargetException, IllegalAccessException {

        for (ActivityRecordDto item : items) {

            // 查询出关联的AppUser表信息
            AppUser JoinUserEntity = AppUserMapper.selectById(item.getJoinUserId());
            item.setJoinUserDto(JoinUserEntity != null ? JoinUserEntity.MapToDto() : new AppUserDto());

            // 查询出关联的Activity表信息
            Activity ActivityEntity = ActivityMapper.selectById(item.getActivityId());
            item.setActivityDto(ActivityEntity != null ? ActivityEntity.MapToDto() : new ActivityDto());
        }

        return items;
    }

    /**
     * 活动登记分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<ActivityRecordDto> List(ActivityRecordPagedInput input) {
        // 构建where条件+排序
        LambdaQueryWrapper<ActivityRecord> queryWrapper = BuilderQuery(input);

        // 按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(ActivityRecord::getCreationTime);
        // 构建一个分页查询的model
        Page<ActivityRecord> page = new Page<>(input.getPage(), input.getLimit());
        // 从数据库进行分页查询获取活动登记数据
        IPage<ActivityRecord> pageRecords = ActivityRecordMapper.selectPage(page, queryWrapper);
        // 获取所有满足条件的数据行数
        Long totalCount = ActivityRecordMapper.selectCount(queryWrapper);
        // 把ActivityRecord实体转换成ActivityRecord传输模型
        List<ActivityRecordDto> items = Extension.copyBeanList(pageRecords.getRecords(), ActivityRecordDto.class);

        DispatchItem(items);
        // 返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个活动登记查询
     */
    @SneakyThrows
    @Override
    public ActivityRecordDto Get(ActivityRecordPagedInput input) {
        if (input.getId() == null) {
            return new ActivityRecordDto();
        }

        PagedResult<ActivityRecordDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new ActivityRecordDto());
    }

    /**
     * 活动登记创建或者修改
     */
    @SneakyThrows
    @Override
    public ActivityRecordDto CreateOrEdit(ActivityRecordDto input) {

        // 声明一个活动登记实体
        ActivityRecord ActivityRecord = input.MapToEntity();
        // 调用数据库的增加或者修改方法
        saveOrUpdate(ActivityRecord);
        // 把传输模型返回给前端
        return ActivityRecord.MapToDto();
    }

    /**
     * 活动预约
     */
    @SneakyThrows
    @Override
    public ActivityRecordDto Appoint(ActivityRecordDto input) {

        var activity = ActivityMapper.selectById(input.getActivityId());

        List<ActivityRecord> activityRecords = ActivityRecordMapper.selectList(
                Wrappers.<ActivityRecord>lambdaQuery().eq(ActivityRecord::getJoinUserId, input.getJoinUserId())
                        .eq(ActivityRecord::getIsCancel, false)
                        .in(ActivityRecord::getActivityAuditStatus,
                                ActivityAuditStatusEnum.待审核.index(),
                                ActivityAuditStatusEnum.审核通过.index())
                        .eq(ActivityRecord::getActivityId, input.getActivityId()));

        if (activityRecords.size() > 0) {
            throw new CustomException("您已经预约过该活动");
        }

        // 查看报名的人数
        Long count = ActivityRecordMapper.selectCount(
                Wrappers.<ActivityRecord>lambdaQuery()
                        .eq(ActivityRecord::getIsCancel, false)
                        .in(ActivityRecord::getActivityAuditStatus,
                                ActivityAuditStatusEnum.待审核.index(),
                                ActivityAuditStatusEnum.审核通过.index())
                        .eq(ActivityRecord::getActivityId, input.getActivityId()));

        if (count >= activity.getLimitPeople()) {
            throw new CustomException("报名人数已满");
        }
        // 查询报名人信息
        AppUser joinUser = AppUserMapper.selectById(input.getJoinUserId());
        if (Extension.isNullOrEmpty(joinUser.getEmail())) {
            throw new CustomException("报名人邮箱不能为空");
        }

        // 随机一个10位数的数字的签到码
        Random random = new Random();
        input.setCheckCode(random.nextInt(1000000000) + "");
        boolean isSend = EmailUtil.sendTextMail(joinUser.getEmail(), "活动签到码", "您的签到码是：" + input.getCheckCode());
        if (!isSend) {
            throw new CustomException("报名失败,你的邮箱可能填写错误");
        }
        // 声明一个活动登记实体
        ActivityRecord ActivityRecord = input.MapToEntity();
        // 调用数据库的增加或者修改方法
        saveOrUpdate(ActivityRecord);
        // 把传输模型返回给前端
        return ActivityRecord.MapToDto();
    }

    /**
     * 报名取消
     */
    @Override
    public void Cancel(ActivityRecordDto input) {

        Activity activity = ActivityMapper.selectById(input.getActivityId());
        if (activity.getEndJoinTime().isBefore(LocalDateTime.now().plusMinutes(30))) {
            throw new CustomException("离活动报名时间不足30分钟不可以取消报名");
        }
        // 查询本月是否取消次数超过3次
        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());
        Long cancelCount = ActivityRecordMapper.selectCount(
                Wrappers.<ActivityRecord>lambdaQuery().eq(ActivityRecord::getJoinUserId, input.getJoinUserId())
                        .ge(ActivityRecord::getCancelTime, startOfMonth)
                        .le(ActivityRecord::getCancelTime, endOfMonth));
        if (cancelCount >= 3) {
            throw new CustomException("本月取消次数已超过3次");
        }

        ActivityRecord entity = ActivityRecordMapper.selectById(input.getId());
        entity.setIsCancel(true);
        entity.setCancelTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }

    /**
     * 报名签到
     */
    @Override
    public void CheckIn(ActivityRecordDto input) {
        Activity activity = ActivityMapper.selectById(input.getActivityId());
        if (activity.getEndActiveTime().isBefore(LocalDateTime.now())) {
            throw new CustomException("活动已经时间已结束");
        }
        if (activity.getBeginActiveTime().isAfter(LocalDateTime.now().plusHours(2))) {
            throw new CustomException("活动开始时间超过2小时，暂时无法签到");
        }
        if (!input.getInputCheckCode().equals(input.getCheckCode())) {
            throw new CustomException("签到码错误");
        }
        if (input.getIsCancel()) {
            throw new CustomException("报名已取消");
        }



        ActivityRecord entity = ActivityRecordMapper.selectById(input.getId());
        entity.setIsCheck(true);
        entity.setCheckTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }

    /**
     * 活动登记删除
     */
    @Override
    public void Delete(IdInput input) {
        ActivityRecord entity = ActivityRecordMapper.selectById(input.getId());
        ActivityRecordMapper.deleteById(entity);
    }

    /**
     * 活动登记批量删除
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
