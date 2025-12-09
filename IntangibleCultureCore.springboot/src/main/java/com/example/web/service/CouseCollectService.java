package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.CouseCollectDto;
import com.example.web.dto.query.CouseCollectPagedInput;
import com.example.web.entity.CouseCollect;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;

/**
 * 课程收藏功能的Service接口的定义清单
 */
public interface CouseCollectService extends IService<CouseCollect> {

    /**
     * 课程收藏的分页查询方法接口定义
     */
    public PagedResult<CouseCollectDto> List(CouseCollectPagedInput input) ;
    /**
     * 课程收藏的新增或者修改方法接口定义
     */
    public CouseCollectDto CreateOrEdit(CouseCollectDto input);

     /**
     * 获取课程收藏信息
     */
    public CouseCollectDto Get(CouseCollectPagedInput input);
 	 /**
     * 课程收藏删除
     */
    public void Delete(IdInput input);

    /**
     * 课程收藏批量删除
     */
    public void BatchDelete(IdsInput input);
  

}
