package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.CulturalTypeDto;
import com.example.web.dto.query.CulturalTypePagedInput;
import com.example.web.entity.CulturalType;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.PagedResult;

/**
 * 非遗文化类别功能的Service接口的定义清单
 */
public interface CulturalTypeService extends IService<CulturalType> {

    /**
     * 非遗文化类别的分页查询方法接口定义
     */
    public PagedResult<CulturalTypeDto> List(CulturalTypePagedInput input) ;
    /**
     * 非遗文化类别的新增或者修改方法接口定义
     */
    public CulturalTypeDto CreateOrEdit(CulturalTypeDto input);

     /**
     * 获取非遗文化类别信息
     */
    public CulturalTypeDto Get(CulturalTypePagedInput input);
 	 /**
     * 非遗文化类别删除
     */
    public void Delete(IdInput input);

    /**
     * 非遗文化类别批量删除
     */
    public void BatchDelete(IdsInput input);
  

}
