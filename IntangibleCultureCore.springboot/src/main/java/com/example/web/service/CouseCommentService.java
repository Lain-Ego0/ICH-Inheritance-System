package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.CouseCommentDto;
import com.example.web.dto.query.CouseCommentPagedInput;
import com.example.web.entity.CouseComment;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;

/**
 * 课程评价功能的Service接口的定义清单
 */
public interface CouseCommentService extends IService<CouseComment> {

    /**
     * 课程评价的分页查询方法接口定义
     */
    public PagedResult<CouseCommentDto> List(CouseCommentPagedInput input) ;
    /**
     * 课程评价的新增或者修改方法接口定义
     */
    public CouseCommentDto CreateOrEdit(CouseCommentDto input);

     /**
     * 获取课程评价信息
     */
    public CouseCommentDto Get(CouseCommentPagedInput input);
 	 /**
     * 课程评价删除
     */
    public void Delete(IdInput input);

    /**
     * 课程评价批量删除
     */
    public void BatchDelete(IdsInput input);
  

}
