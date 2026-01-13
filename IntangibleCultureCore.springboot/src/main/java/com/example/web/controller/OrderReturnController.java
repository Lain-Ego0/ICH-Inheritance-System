package com.example.web.controller;

import com.example.web.dto.OrderReturnDto;
import com.example.web.dto.query.OrderReturnPagedInput;
import com.example.web.mapper.OrderReturnMapper;
import com.example.web.service.OrderReturnService;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * 订单退货控制器
 */
@RestController()
@RequestMapping("/OrderReturn")
public class OrderReturnController {
    @Autowired()
    private OrderReturnService  OrderReturnService;
    @Autowired()
    private OrderReturnMapper  OrderReturnMapper;

    /**
     * 订单退货分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<OrderReturnDto> List(@RequestBody OrderReturnPagedInput input) {
        return  OrderReturnService.List(input);
    }

    /**
     * 单个订单退货查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public OrderReturnDto Get(@RequestBody OrderReturnPagedInput input) {

        return  OrderReturnService.Get(input);
    }

    /**
     * 订单退货创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public OrderReturnDto CreateOrEdit(@RequestBody OrderReturnDto input) throws Exception {
        return  OrderReturnService.CreateOrEdit(input);
    }

    /**
     * 订单退货删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input) {
        OrderReturnService.Delete(input);
    }

    /**
     * 订单退货批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input) {
        OrderReturnService.BatchDelete(input);
    }

    /**
     * 订单退货导出
     */
    @RequestMapping(value = "/Export", method = RequestMethod.GET)
    public void Export(@RequestParam String query, HttpServletResponse response) throws IOException {
        OrderReturnService.Export(query, response);
    }

    /**
     * 撤销申请
     */
    @RequestMapping(value = "/Discard", method = RequestMethod.POST)
    public void Discard(@RequestBody OrderReturnDto input) throws Exception {
        OrderReturnService.Discard(input);
    }
    /**
     * 商家处理
     */
    @RequestMapping(value = "/SellerDispatch", method = RequestMethod.POST)
    public void SellerDispatch(@RequestBody OrderReturnDto input) throws Exception {
        OrderReturnService.SellerDispatch(input);
    }
}
