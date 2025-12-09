package com.example.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.web.dto.ActivityDto;
import com.example.web.dto.query.ActivityPagedInput;
import com.example.web.mapper.ActivityMapper;
import com.example.web.service.ActivityService;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import com.example.web.tools.dto.SelectResult;

import lombok.SneakyThrows;

/**
 * 活动控制器
 */
@RestController()
@RequestMapping("/Activity")
public class ActivityController {
    @Autowired()
    private ActivityService ActivityService;
    @Autowired()
    private ActivityMapper ActivityMapper;

    /**
     * 活动分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<ActivityDto> List(@RequestBody ActivityPagedInput input) {
        return ActivityService.List(input);
    }

    /**
     * 单个活动查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public ActivityDto Get(@RequestBody ActivityPagedInput input) {

        return ActivityService.Get(input);
    }

    /**
     * 活动创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public ActivityDto CreateOrEdit(@RequestBody ActivityDto input) throws Exception {
        return ActivityService.CreateOrEdit(input);
    }

    /**
     * 活动删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input) {
        ActivityService.Delete(input);
    }

    /**
     * 活动批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input) {
        ActivityService.BatchDelete(input);
    }

    /**
     * 查询活动分类
     */
    @RequestMapping(value = "/GetActivityTypeList", method = RequestMethod.POST)
    public PagedResult<SelectResult> GetActivityTypeList() {
        return ActivityService.GetActivityType();
    }

}
