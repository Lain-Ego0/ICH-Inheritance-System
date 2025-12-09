package com.example.web.controller;

import com.example.web.dto.CouseTypeDto;
import com.example.web.dto.query.CouseTypePagedInput;
import com.example.web.mapper.CouseTypeMapper;
import com.example.web.service.CouseTypeService;
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
 * 课程分类控制器
 */
@RestController()
@RequestMapping("/CouseType")
public class CouseTypeController {
    @Autowired()
    private  CouseTypeService CouseTypeService;
    @Autowired()
    private CouseTypeMapper CouseTypeMapper;
    /**
     * 课程分类分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<CouseTypeDto> List(@RequestBody CouseTypePagedInput input)  {
        return CouseTypeService.List(input);
    }
     /**
     * 单个课程分类查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public CouseTypeDto Get(@RequestBody CouseTypePagedInput input) {

        return CouseTypeService.Get(input);
    }
  
    /**
     * 课程分类创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public CouseTypeDto CreateOrEdit(@RequestBody CouseTypeDto input) throws Exception {
        return CouseTypeService.CreateOrEdit(input);
    }
    /**
     * 课程分类删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input)
    {
        CouseTypeService.Delete(input);
    }

    /**
     * 课程分类批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input)
    {
        CouseTypeService.BatchDelete(input);
    }
  

 
}
