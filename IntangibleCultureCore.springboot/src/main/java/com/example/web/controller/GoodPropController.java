package com.example.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.web.dto.GoodPropDto;
import com.example.web.dto.query.GoodPropPagedInput;
import com.example.web.mapper.GoodPropMapper;
import com.example.web.service.GoodPropService;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;

import lombok.SneakyThrows;

/**
 * 文创品属性控制器
 */
@RestController()
@RequestMapping("/GoodProp")
public class GoodPropController {
    @Autowired()
    private GoodPropService GoodPropService;
    @Autowired()
    private GoodPropMapper GoodPropMapper;

    /**
     * 文创品属性分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<GoodPropDto> List(@RequestBody GoodPropPagedInput input) {
        return GoodPropService.List(input);
    }

    /**
     * 单个文创品属性查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public GoodPropDto Get(@RequestBody GoodPropPagedInput input) {

        return GoodPropService.Get(input);
    }

    /**
     * 文创品属性创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public GoodPropDto CreateOrEdit(@RequestBody GoodPropDto input) throws Exception {
        return GoodPropService.CreateOrEdit(input);
    }

    /**
     * 文创品属性删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input) {
        GoodPropService.Delete(input);
    }

    /**
     * 文创品属性批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input) {
        GoodPropService.BatchDelete(input);
    }

}
