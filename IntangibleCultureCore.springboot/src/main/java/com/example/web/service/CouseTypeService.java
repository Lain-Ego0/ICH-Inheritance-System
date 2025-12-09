package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.CouseTypeDto;
import com.example.web.dto.query.CouseTypePagedInput;
import com.example.web.entity.CouseType;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;

/**
 * 课程分类功能的Service接口的定义清单
 */
public interface CouseTypeService extends IService<CouseType> {

    /**
     * 课程分类的分页查询方法接口定义
     */
    public PagedResult<CouseTypeDto> List(CouseTypePagedInput input);

    /**
     * 课程分类的新增或者修改方法接口定义
     */
    public CouseTypeDto CreateOrEdit(CouseTypeDto input);

    /**
     * 获取课程分类信息
     */
    public CouseTypeDto Get(CouseTypePagedInput input);

    /**
     * 课程分类删除
     */
    public void Delete(IdInput input);

    /**
     * 课程分类批量删除
     */
    public void BatchDelete(IdsInput input);

}
