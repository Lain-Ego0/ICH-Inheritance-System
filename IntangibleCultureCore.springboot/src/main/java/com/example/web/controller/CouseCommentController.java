package com.example.web.controller;

import com.example.web.dto.CouseCommentDto;
import com.example.web.dto.query.CouseCommentPagedInput;
import com.example.web.mapper.CouseCommentMapper;
import com.example.web.service.CouseCommentService;
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
 * 课程评价控制器
 */
@RestController()
@RequestMapping("/CouseComment")
public class CouseCommentController {
    @Autowired()
    private  CouseCommentService CouseCommentService;
    @Autowired()
    private CouseCommentMapper CouseCommentMapper;
    /**
     * 课程评价分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<CouseCommentDto> List(@RequestBody CouseCommentPagedInput input)  {
        return CouseCommentService.List(input);
    }
     /**
     * 单个课程评价查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public CouseCommentDto Get(@RequestBody CouseCommentPagedInput input) {

        return CouseCommentService.Get(input);
    }
  
    /**
     * 课程评价创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public CouseCommentDto CreateOrEdit(@RequestBody CouseCommentDto input) throws Exception {
        return CouseCommentService.CreateOrEdit(input);
    }
    /**
     * 课程评价删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input)
    {
        CouseCommentService.Delete(input);
    }

    /**
     * 课程评价批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input)
    {
        CouseCommentService.BatchDelete(input);
    }
  

 
}
