package com.example.web.controller;

import com.example.web.dto.CouseCollectDto;
import com.example.web.dto.query.CouseCollectPagedInput;
import com.example.web.mapper.CouseCollectMapper;
import com.example.web.service.CouseCollectService;
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
 * 课程收藏控制器
 */
@RestController()
@RequestMapping("/CouseCollect")
public class CouseCollectController {
    @Autowired()
    private  CouseCollectService CouseCollectService;
    @Autowired()
    private CouseCollectMapper CouseCollectMapper;
    /**
     * 课程收藏分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<CouseCollectDto> List(@RequestBody CouseCollectPagedInput input)  {
        return CouseCollectService.List(input);
    }
     /**
     * 单个课程收藏查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public CouseCollectDto Get(@RequestBody CouseCollectPagedInput input) {

        return CouseCollectService.Get(input);
    }
  
    /**
     * 课程收藏创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public CouseCollectDto CreateOrEdit(@RequestBody CouseCollectDto input) throws Exception {
        return CouseCollectService.CreateOrEdit(input);
    }
    /**
     * 课程收藏删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input)
    {
        CouseCollectService.Delete(input);
    }

    /**
     * 课程收藏批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input)
    {
        CouseCollectService.BatchDelete(input);
    }
  

 
}
