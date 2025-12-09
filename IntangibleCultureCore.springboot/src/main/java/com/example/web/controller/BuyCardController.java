package com.example.web.controller;

import com.example.web.dto.BuyCardDto;
import com.example.web.dto.BuyCardShopSummaryDto;
import com.example.web.dto.query.BuyCardPagedInput;
import com.example.web.mapper.BuyCardMapper;
import com.example.web.service.BuyCardService;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 购物车控制器
 */
@RestController()
@RequestMapping("/BuyCard")
public class BuyCardController {
    @Autowired()
    private BuyCardService  BuyCardService;
    @Autowired()
    private BuyCardMapper  BuyCardMapper;

    /**
     * 购物车分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<BuyCardDto> List(@RequestBody BuyCardPagedInput input) {
        return  BuyCardService.List(input);
    }

    /**
     * 得到按店铺分组的购物车
     */
    @RequestMapping(value = "/ShopSummaryList", method = RequestMethod.POST)
    @SneakyThrows
    public List<BuyCardShopSummaryDto> ShopSummaryList(@RequestBody BuyCardPagedInput input) {
        return  BuyCardService.ShopSummaryList(input);
    }


    /**
     * 单个购物车查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public BuyCardDto Get(@RequestBody BuyCardPagedInput input) {

        return  BuyCardService.Get(input);
    }

    /**
     * 购物车创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public BuyCardDto CreateOrEdit(@RequestBody BuyCardDto input) throws Exception {
        return  BuyCardService.CreateOrEdit(input);
    }

    /**
     * 购物车删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input) {
        BuyCardService.Delete(input);
    }

    /**
     * 购物车批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input) {
        BuyCardService.BatchDelete(input);
    }


}
