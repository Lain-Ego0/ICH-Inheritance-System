package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.ShopCollectDto;
import com.example.web.dto.query.ShopCollectPagedInput;
import com.example.web.entity.ShopCollect;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;

/**
 * 店铺收藏功能的Service接口的定义清单
 */
public interface ShopCollectService extends IService<ShopCollect> {

    /**
     * 店铺收藏的分页查询方法接口定义
     */
    public PagedResult<ShopCollectDto> List(ShopCollectPagedInput input) ;
    /**
     * 店铺收藏的新增或者修改方法接口定义
     */
    public ShopCollectDto CreateOrEdit(ShopCollectDto input);

     /**
     * 获取店铺收藏信息
     */
    public ShopCollectDto Get(ShopCollectPagedInput input);
 	 /**
     * 店铺收藏删除
     */
    public void Delete(IdInput input);

    /**
     * 店铺收藏批量删除
     */
    public void BatchDelete(IdsInput input);
  

}
