package com.example.web.controller;

import com.example.web.dto.GoodTypeDto;
import com.example.web.dto.query.GoodTypePagedInput;
import com.example.web.mapper.GoodTypeMapper;
import com.example.web.service.GoodTypeService;
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
 * 文创品分类控制器
 */
@RestController()
@RequestMapping("/GoodType")
public class GoodTypeController {
    @Autowired()
    private GoodTypeService GoodTypeService;
    @Autowired()
    private GoodTypeMapper GoodTypeMapper;

    /**
     * 文创品分类分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<GoodTypeDto> List(@RequestBody GoodTypePagedInput input) {
        return GoodTypeService.List(input);
    }

    /**
     * 单个文创品分类查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public GoodTypeDto Get(@RequestBody GoodTypePagedInput input) {

        return GoodTypeService.Get(input);
    }

    /**
     * 文创品分类创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public GoodTypeDto CreateOrEdit(@RequestBody GoodTypeDto input) throws Exception {
        return GoodTypeService.CreateOrEdit(input);
    }

    /**
     * 文创品分类删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input) {
        GoodTypeService.Delete(input);
    }

    /**
     * 文创品分类批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input) {
        GoodTypeService.BatchDelete(input);
    }

}
