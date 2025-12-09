package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.GoodTypeDto;
import com.example.web.dto.query.GoodTypePagedInput;
import com.example.web.entity.GoodType;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;

/**
 * 文创品分类功能的Service接口的定义清单
 */
public interface GoodTypeService extends IService<GoodType> {

    /**
     * 文创品分类的分页查询方法接口定义
     */
    public PagedResult<GoodTypeDto> List(GoodTypePagedInput input);

    /**
     * 文创品分类的新增或者修改方法接口定义
     */
    public GoodTypeDto CreateOrEdit(GoodTypeDto input);

    /**
     * 获取文创品分类信息
     */
    public GoodTypeDto Get(GoodTypePagedInput input);

    /**
     * 文创品分类删除
     */
    public void Delete(IdInput input);

    /**
     * 文创品分类批量删除
     */
    public void BatchDelete(IdsInput input);

}
