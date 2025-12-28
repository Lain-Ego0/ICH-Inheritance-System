package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.OrderInfoDto;
import com.example.web.dto.query.OrderInfoPagedInput;
import com.example.web.entity.OrderInfo;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

/**
 * 订单功能的Service接口的定义清单
 */
public interface OrderInfoService extends IService<OrderInfo> {

    /**
     * 订单的分页查询方法接口定义
     */
    public PagedResult<OrderInfoDto> List(OrderInfoPagedInput input);

    /**
     * 订单的新增或者修改方法接口定义
     */
    public OrderInfoDto CreateOrEdit(OrderInfoDto input);

    /**
     * 获取订单信息
     */
    public OrderInfoDto Get(OrderInfoPagedInput input);

    /**
     * 订单删除
     */
    public void Delete(IdInput input);

    /**
     * 订单批量删除
     */
    public void BatchDelete(IdsInput input);

    /**
     * 订单导出
     */
    public void Export(@RequestParam String query, HttpServletResponse response) throws IOException;

    /**
     * 下单
     */
    public String ToOrder(List<OrderInfoDto> input) throws InvocationTargetException, IllegalAccessException;

    /**
     * 支付宝支付
     */
    String AliPay(OrderInfoDto input) throws Exception;

    /**
     * 支付宝支付检测
     */
    void CheckAliPayCallBack();

    void CheckExpire();

    /**
     * 订单发货
     */
    OrderInfoDto SendGood(OrderInfoDto input) throws InvocationTargetException, IllegalAccessException;

    /**
     * 到店取货
     */
    OrderInfoDto SelfPickup(OrderInfoDto input) throws InvocationTargetException, IllegalAccessException;

    /**
     * 订单收货
     */

    OrderInfoDto GetGood(OrderInfoDto input) throws InvocationTargetException, IllegalAccessException;

    /**
     * 统计数据
     */
    HashMap<String, Object> GetOrderSummaryData(OrderInfoPagedInput input);

    /**
     * 得到最近的收入情况
     */
    HashMap<String, Object> GetOrderIncome(OrderInfoPagedInput input);

    /**
     * 得到店铺热门文创品的销量
     */
    List<Object> GetGoodSales(OrderInfoPagedInput input);

    /**
     * 按4个季节统计订单的数量
     */
    List<Object> GetSeasonGoodSalesEchart(OrderInfoPagedInput input);

    /**
     * 按24小时统计订单的数量
     */
    List<Object> Get24HourOrderCountEchart(OrderInfoPagedInput input);
}
