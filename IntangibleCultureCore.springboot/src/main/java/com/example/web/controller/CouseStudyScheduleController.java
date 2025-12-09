package com.example.web.controller;

import com.example.web.dto.CouseStudyScheduleDto;
import com.example.web.dto.query.CouseStudySchedulePagedInput;
import com.example.web.mapper.CouseStudyScheduleMapper;
import com.example.web.service.CouseStudyScheduleService;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 课程学习进度控制器
 */
@RestController()
@RequestMapping("/CouseStudySchedule")
public class CouseStudyScheduleController {
    @Autowired()
    private CouseStudyScheduleService CouseStudyScheduleService;
    @Autowired()
    private CouseStudyScheduleMapper CouseStudyScheduleMapper;

    /**
     * 课程学习进度分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<CouseStudyScheduleDto> List(@RequestBody CouseStudySchedulePagedInput input) {
        return CouseStudyScheduleService.List(input);
    }

    /**
     * 单个课程学习进度查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public CouseStudyScheduleDto Get(@RequestBody CouseStudySchedulePagedInput input) {

        return CouseStudyScheduleService.Get(input);
    }

    /**
     * 课程学习进度创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public CouseStudyScheduleDto CreateOrEdit(@RequestBody CouseStudyScheduleDto input) throws Exception {
        return CouseStudyScheduleService.CreateOrEdit(input);
    }

    /**
     * 课程学习进度删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input) {
        CouseStudyScheduleService.Delete(input);
    }

    /**
     * 课程学习进度批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input) {
        CouseStudyScheduleService.BatchDelete(input);
    }

    /**
     * 更新时长
     */
    @RequestMapping(value = "/UpdateTotalDurtion", method = RequestMethod.POST)
    public CouseStudyScheduleDto UpdateTotalDurtion(@RequestBody CouseStudyScheduleDto input) {
        return CouseStudyScheduleService.UpdateTotalDurtion(input);
    }

}
