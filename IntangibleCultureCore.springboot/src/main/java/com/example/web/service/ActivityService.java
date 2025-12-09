package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.ActivityDto;
import com.example.web.dto.query.ActivityPagedInput;
import com.example.web.entity.Activity;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import com.example.web.tools.dto.SelectResult;

/**
 * 活动功能的Service接口的定义清单
 */
public interface ActivityService extends IService<Activity> {

    /**
     * 活动的分页查询方法接口定义
     */
    public PagedResult<ActivityDto> List(ActivityPagedInput input);

    /**
     * 活动的新增或者修改方法接口定义
     */
    public ActivityDto CreateOrEdit(ActivityDto input);

    /**
     * 获取活动信息
     */
    public ActivityDto Get(ActivityPagedInput input);

    /**
     * 活动删除
     */
    public void Delete(IdInput input);

    /**
     * 活动批量删除
     */
    public void BatchDelete(IdsInput input);

    /**
     * 自动更新活动状态
     */
    public void AutomaticallyActivityChangeStatus();

    /**
     * 查询活动分类
     */
    public PagedResult<SelectResult> GetActivityType();
}
