package com.example.web.controller;

import com.example.web.dto.OrderDetDto;
import com.example.web.dto.query.OrderDetPagedInput;
import com.example.web.mapper.OrderDetMapper;
import com.example.web.service.OrderDetService;
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
 * 订单明细控制器
 */
@RestController()
@RequestMapping("/OrderDet")
public class OrderDetController {
    @Autowired()
    private  OrderDetService  OrderDetService;
    @Autowired()
    private OrderDetMapper  OrderDetMapper;
    /**
     * 订单明细分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<OrderDetDto> List(@RequestBody OrderDetPagedInput input)  {
        return  OrderDetService.List(input);
    }
     /**
     * 单个订单明细查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public OrderDetDto Get(@RequestBody OrderDetPagedInput input) {

        return  OrderDetService.Get(input);
    }
  
    /**
     * 订单明细创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public OrderDetDto CreateOrEdit(@RequestBody OrderDetDto input) throws Exception {
        return  OrderDetService.CreateOrEdit(input);
    }
    /**
     * 订单明细删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input)
    {
        OrderDetService.Delete(input);
    }

    /**
     * 订单明细批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input)
    {
        OrderDetService.BatchDelete(input);
    }
  

 
}
