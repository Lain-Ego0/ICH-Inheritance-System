package com.example.web.controller;

import com.example.web.dto.OrderInfoDto;
import com.example.web.dto.query.OrderInfoPagedInput;
import com.example.web.mapper.OrderInfoMapper;
import com.example.web.service.OrderInfoService;
import com.example.web.tools.Extension;
import com.example.web.tools.HttpUtils;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import com.example.web.tools.dto.ResponseData;
import com.example.web.tools.exception.CustomException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单控制器
 */
@RestController()
@RequestMapping("/OrderInfo")
public class OrderInfoController {
    @Autowired()
    private OrderInfoService OrderInfoService;
    @Autowired()
    private OrderInfoMapper OrderInfoMapper;

    /**
     * 订单分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<OrderInfoDto> List(@RequestBody OrderInfoPagedInput input) {
        return OrderInfoService.List(input);
    }

    /**
     * 单个订单查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public OrderInfoDto Get(@RequestBody OrderInfoPagedInput input) {

        return OrderInfoService.Get(input);
    }

    /**
     * 订单创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public OrderInfoDto CreateOrEdit(@RequestBody OrderInfoDto input) throws Exception {
        return OrderInfoService.CreateOrEdit(input);
    }

    /**
     * 订单删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input) {
        OrderInfoService.Delete(input);
    }

    /**
     * 订单批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input) {
        OrderInfoService.BatchDelete(input);
    }

    /**
     * 订单导出
     */
    @RequestMapping(value = "/Export", method = RequestMethod.GET)
    public void Export(@RequestParam String query, HttpServletResponse response) throws IOException {
        OrderInfoService.Export(query, response);
    }

    /**
     * 下单
     */
    @RequestMapping(value = "/ToOrder", method = RequestMethod.POST)
    public ResponseData<String> ToOrder(@RequestBody List<OrderInfoDto> input) throws Exception {
        String payOrderNo = OrderInfoService.ToOrder(input);
        return ResponseData.GetResponseDataInstance(payOrderNo, "成功", true);
    }

    /**
     * 支付宝支付
     */
    @RequestMapping(value = "/AliPay", method = RequestMethod.POST)
    public ResponseData<String> AliPay(@RequestBody OrderInfoDto input) throws Exception {
        String payOrderNo = OrderInfoService.AliPay(input);
        return ResponseData.GetResponseDataInstance(payOrderNo, "成功", true);
    }

    /**
     * 发货
     */
    @RequestMapping(value = "/SendGood", method = RequestMethod.POST)
    public void SendGood(@RequestBody OrderInfoDto input) throws Exception {
        OrderInfoService.SendGood(input);
    }

    /**
     * 收货
     */
    @RequestMapping(value = "/GetGood", method = RequestMethod.POST)
    public void GetGood(@RequestBody OrderInfoDto input) throws Exception {
        OrderInfoService.GetGood(input);
    }

    /**
     * 物流查询
     */
    @RequestMapping(value = "/QueryExpress", method = RequestMethod.POST)
    public ResponseData<String> QueryExpress(@RequestBody OrderInfoPagedInput input) throws Exception {

        OrderInfoDto orderInfoDto = OrderInfoService.Get(input);
        if (Extension.isNotNullOrEmpty(orderInfoDto.getExpressInfo())) {
            return ResponseData.GetResponseDataInstance(orderInfoDto.getExpressInfo(), "成功", true);
        }

        Map<String, String> header = new HashMap<>();
        // 接口地址
        // https://market.aliyun.com/apimarket/detail/cmapi00065648?accounttraceid=276cbf1467094f1da05d4e7f783bb068mwjx
        header.put("Authorization", "APPCODE " + "a3e3af632e6b47e3801f7e141af83031");
        var rs = HttpUtils.Get("https://sxexprev2.market.alicloudapi.com/express/query/v2?no=" + input.getLogisticsNo(),
                header);
        if (Extension.isNotNullOrEmpty(rs)) {
            orderInfoDto.setExpressInfo(rs);
            OrderInfoService.CreateOrEdit(orderInfoDto);
        } else {
            throw new CustomException("物流单号不存在");
        }

        return ResponseData.GetResponseDataInstance(rs, "成功", true);
    }

    /**
     * 统计数据
     */
    @RequestMapping(value = "/GetOrderSummaryData", method = RequestMethod.POST)
    public HashMap<String, Object> GetOrderSummaryData(@RequestBody OrderInfoPagedInput input) {
        return OrderInfoService.GetOrderSummaryData(input);
    }

    /**
     * 得到最近的收入情况
     */
    @RequestMapping(value = "/GetOrderIncome", method = RequestMethod.POST)
    public HashMap<String, Object> GetOrderIncome(@RequestBody OrderInfoPagedInput input) {
        return OrderInfoService.GetOrderIncome(input);
    }

    /**
     * 得到店铺热门文创品的销量
     */
    @RequestMapping(value = "/GetGoodSales", method = RequestMethod.POST)
    public List<Object> GetGoodSales(@RequestBody OrderInfoPagedInput input) {
        return OrderInfoService.GetGoodSales(input);
    }

    /**
     * 按4个季节统计订单的数量
     */
    @RequestMapping(value = "/GetSeasonGoodSalesEchart", method = RequestMethod.POST)
    public List<Object> GetSeasonGoodSalesEchart(@RequestBody OrderInfoPagedInput input) {
        return OrderInfoService.GetSeasonGoodSalesEchart(input);
    }

    /**
     * 按24小时统计订单的数量
     */
    @RequestMapping(value = "/GetHourGoodSalesEchart", method = RequestMethod.POST)
    public List<Object> GetHourGoodSalesEchart(@RequestBody OrderInfoPagedInput input) {
        return OrderInfoService.Get24HourOrderCountEchart(input);
    }

    /**
     * 到店取货
     */
    @RequestMapping(value = "/SelfPickup", method = RequestMethod.POST)
    public void SelfPickup(@RequestBody OrderInfoDto input) throws Exception {
        OrderInfoService.SelfPickup(input);
    }
}
