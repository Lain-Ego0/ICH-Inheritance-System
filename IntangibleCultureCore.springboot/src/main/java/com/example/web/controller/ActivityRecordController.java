package com.example.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.web.dto.ActivityRecordDto;
import com.example.web.dto.query.ActivityRecordPagedInput;
import com.example.web.mapper.ActivityRecordMapper;
import com.example.web.service.ActivityRecordService;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;

import lombok.SneakyThrows;

/**
 * 活动登记控制器
 */
@RestController()
@RequestMapping("/ActivityRecord")
public class ActivityRecordController {
    @Autowired()
    private ActivityRecordService ActivityRecordService;
    @Autowired()
    private ActivityRecordMapper ActivityRecordMapper;

    /**
     * 活动登记分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<ActivityRecordDto> List(@RequestBody ActivityRecordPagedInput input) {
        return ActivityRecordService.List(input);
    }

    /**
     * 单个活动登记查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public ActivityRecordDto Get(@RequestBody ActivityRecordPagedInput input) {

        return ActivityRecordService.Get(input);
    }

    /**
     * 活动登记创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public ActivityRecordDto CreateOrEdit(@RequestBody ActivityRecordDto input) throws Exception {
        return ActivityRecordService.CreateOrEdit(input);
    }

    /**
     * 活动登记删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input) {
        ActivityRecordService.Delete(input);
    }

    /**
     * 活动登记批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input) {
        ActivityRecordService.BatchDelete(input);
    }

    /**
     * 活动预约
     */
    @RequestMapping(value = "/Appoint", method = RequestMethod.POST)
    public ActivityRecordDto Appoint(@RequestBody ActivityRecordDto input) {
        return ActivityRecordService.Appoint(input);
    }

    /**
     * 报名取消
     */
    @RequestMapping(value = "/Cancel", method = RequestMethod.POST)
    public void Cancel(@RequestBody ActivityRecordDto input) {
        ActivityRecordService.Cancel(input);
    }

    /**
     * 报名签到
     */
    @RequestMapping(value = "/CheckIn", method = RequestMethod.POST)
    public void CheckIn(@RequestBody ActivityRecordDto input) {
        ActivityRecordService.CheckIn(input);
    }

}
