package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.ActivityRecordDto;
import com.example.web.dto.query.ActivityRecordPagedInput;
import com.example.web.entity.ActivityRecord;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;

/**
 * 活动登记功能的Service接口的定义清单
 */
public interface ActivityRecordService extends IService<ActivityRecord> {

    /**
     * 活动登记的分页查询方法接口定义
     */
    public PagedResult<ActivityRecordDto> List(ActivityRecordPagedInput input);

    /**
     * 活动登记的新增或者修改方法接口定义
     */
    public ActivityRecordDto CreateOrEdit(ActivityRecordDto input);

    /**
     * 获取活动登记信息
     */
    public ActivityRecordDto Get(ActivityRecordPagedInput input);

    /**
     * 活动登记删除
     */
    public void Delete(IdInput input);

    /**
     * 活动登记批量删除
     */
    public void BatchDelete(IdsInput input);

    /**
     * 活动预约
     */
    public ActivityRecordDto Appoint(ActivityRecordDto input);

    /**
     * 报名取消
     */
    public void Cancel(ActivityRecordDto input);

    /**
     * 报名签到
     */
    public void CheckIn(ActivityRecordDto input);

}
