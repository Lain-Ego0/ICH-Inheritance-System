package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.BuyCardDto;
import com.example.web.dto.BuyCardShopSummaryDto;
import com.example.web.dto.query.BuyCardPagedInput;
import com.example.web.entity.BuyCard;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import lombok.SneakyThrows;

import java.util.List;

/**
 * 购物车功能的Service接口的定义清单
 */
public interface BuyCardService extends IService<BuyCard> {

    /**
     * 购物车的分页查询方法接口定义
     */
    public PagedResult<BuyCardDto> List(BuyCardPagedInput input) ;
    /**
     * 购物车的新增或者修改方法接口定义
     */
    public BuyCardDto CreateOrEdit(BuyCardDto input);

    /**
     * 得到按店铺分组的购物车
     */
    @SneakyThrows
    List<BuyCardShopSummaryDto> ShopSummaryList(BuyCardPagedInput input);

    /**
     * 获取购物车信息
     */
    public BuyCardDto Get(BuyCardPagedInput input);
 	 /**
     * 购物车删除
     */
    public void Delete(IdInput input);

    /**
     * 购物车批量删除
     */
    public void BatchDelete(IdsInput input);
  

}
