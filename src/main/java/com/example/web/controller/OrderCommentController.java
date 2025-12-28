package com.example.web.controller;

import com.example.web.dto.OrderCommentDto;
import com.example.web.dto.query.OrderCommentPagedInput;
import com.example.web.mapper.OrderCommentMapper;
import com.example.web.service.OrderCommentService;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * 订单评价控制器
 */
@RestController()
@RequestMapping("/OrderComment")
public class OrderCommentController {
    @Autowired()
    private OrderCommentService  OrderCommentService;
    @Autowired()
    private OrderCommentMapper  OrderCommentMapper;

    /**
     * 订单评价分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<OrderCommentDto> List(@RequestBody OrderCommentPagedInput input) {
        return  OrderCommentService.List(input);
    }

    /**
     * 单个订单评价查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public OrderCommentDto Get(@RequestBody OrderCommentPagedInput input) {

        return  OrderCommentService.Get(input);
    }

    /**
     * 订单评价创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public OrderCommentDto CreateOrEdit(@RequestBody OrderCommentDto input) throws Exception {
        return  OrderCommentService.CreateOrEdit(input);
    }

    /**
     * 订单评价删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input) {
        OrderCommentService.Delete(input);
    }

    /**
     * 订单评价批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input) {
        OrderCommentService.BatchDelete(input);
    }

    /**
     * 订单评价导出
     */
    @RequestMapping(value = "/Export", method = RequestMethod.GET)
    public void Export(@RequestParam String query, HttpServletResponse response) throws IOException {
        OrderCommentService.Export(query, response);
    }

    /**
     * 订单评价
     */
    @RequestMapping(value = "/OrderComment", method = RequestMethod.POST)
    public void OrderComment(@RequestBody List<OrderCommentDto> input) throws Exception {
        OrderCommentService.OrderComment(input);
    }


}
