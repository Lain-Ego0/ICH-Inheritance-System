package com.example.web.controller;

import com.example.web.dto.ShopCollectDto;
import com.example.web.dto.query.ShopCollectPagedInput;
import com.example.web.mapper.ShopCollectMapper;
import com.example.web.service.ShopCollectService;
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
 * 店铺收藏控制器
 */
@RestController()
@RequestMapping("/ShopCollect")
public class ShopCollectController {
    @Autowired()
    private  ShopCollectService  ShopCollectService;
    @Autowired()
    private ShopCollectMapper  ShopCollectMapper;
    /**
     * 店铺收藏分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<ShopCollectDto> List(@RequestBody ShopCollectPagedInput input)  {
        return  ShopCollectService.List(input);
    }
     /**
     * 单个店铺收藏查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public ShopCollectDto Get(@RequestBody ShopCollectPagedInput input) {

        return  ShopCollectService.Get(input);
    }
  
    /**
     * 店铺收藏创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public ShopCollectDto CreateOrEdit(@RequestBody ShopCollectDto input) throws Exception {
        return  ShopCollectService.CreateOrEdit(input);
    }
    /**
     * 店铺收藏删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input)
    {
        ShopCollectService.Delete(input);
    }

    /**
     * 店铺收藏批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input)
    {
        ShopCollectService.BatchDelete(input);
    }
  

 
}
