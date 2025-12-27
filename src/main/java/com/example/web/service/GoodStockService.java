package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.GoodStockDto;
import com.example.web.dto.query.GoodStockPagedInput;
import com.example.web.entity.GoodStock;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;

/**
 * 库存记录功能的Service接口的定义清单
 */
public interface GoodStockService extends IService<GoodStock> {

    /**
     * 库存记录的分页查询方法接口定义
     */
    public PagedResult<GoodStockDto> List(GoodStockPagedInput input) ;
    /**
     * 库存记录的新增或者修改方法接口定义
     */
    public GoodStockDto CreateOrEdit(GoodStockDto input);

     /**
     * 获取库存记录信息
     */
    public GoodStockDto Get(GoodStockPagedInput input);
 	 /**
     * 库存记录删除
     */
    public void Delete(IdInput input);

    /**
     * 库存记录批量删除
     */
    public void BatchDelete(IdsInput input);
  

}
