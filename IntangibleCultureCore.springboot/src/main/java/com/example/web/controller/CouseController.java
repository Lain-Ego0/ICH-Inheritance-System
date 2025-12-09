package com.example.web.controller;

import com.example.web.dto.CouseDto;
import com.example.web.dto.query.CousePagedInput;
import com.example.web.mapper.CouseMapper;
import com.example.web.service.CouseService;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 课程控制器
 */
@RestController()
@RequestMapping("/Couse")
public class CouseController {
    @Autowired()
    private CouseService CouseService;
    @Autowired()
    private CouseMapper CouseMapper;

    /**
     * 课程分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<CouseDto> List(@RequestBody CousePagedInput input) {
        return CouseService.List(input);
    }

    /**
     * 单个课程查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public CouseDto Get(@RequestBody CousePagedInput input) {

        return CouseService.Get(input);
    }

    /**
     * 课程创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public CouseDto CreateOrEdit(@RequestBody CouseDto input) throws Exception {
        return CouseService.CreateOrEdit(input);
    }

    /**
     * 课程删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input) {
        CouseService.Delete(input);
    }

    /**
     * 课程批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input) {
        CouseService.BatchDelete(input);
    }

    /**
     * 推荐用户可能喜欢的课程
     */
    @RequestMapping(value = "/Recommend", method = RequestMethod.POST)
    public PagedResult<CouseDto> Recommend(@RequestBody CouseDto input) {
        return CouseService.Recommend(input);
    }

    /**
     * 统计课程报名人数
     */
    @RequestMapping(value = "/CouseEnrollCountEchart", method = RequestMethod.POST)
    public List<Object> CouseEnrollCountEchart() {
        return CouseService.CouseEnrollCountEchart();
    }
}
