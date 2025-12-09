package com.example.web.controller;

import com.example.web.dto.CouseOrderDto;
import com.example.web.dto.query.CouseOrderPagedInput;
import com.example.web.mapper.CouseOrderMapper;
import com.example.web.service.CouseOrderService;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import com.example.web.tools.dto.ResponseData;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 课程订单控制器
 */
@RestController()
@RequestMapping("/CouseOrder")
public class CouseOrderController {
    @Autowired()
    private CouseOrderService CouseOrderService;
    @Autowired()
    private CouseOrderMapper CouseOrderMapper;

    /**
     * 课程订单分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<CouseOrderDto> List(@RequestBody CouseOrderPagedInput input) {
        return CouseOrderService.List(input);
    }

    /**
     * 单个课程订单查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public CouseOrderDto Get(@RequestBody CouseOrderPagedInput input) {

        return CouseOrderService.Get(input);
    }

    /**
     * 课程订单创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public CouseOrderDto CreateOrEdit(@RequestBody CouseOrderDto input) throws Exception {
        return CouseOrderService.CreateOrEdit(input);
    }

    /**
     * 课程订单删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input) {
        CouseOrderService.Delete(input);
    }

    /**
     * 课程订单批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input) {
        CouseOrderService.BatchDelete(input);
    }

    /**
     * 报名
     */
    @RequestMapping(value = "/Enroll", method = RequestMethod.POST)
    public CouseOrderDto Enroll(@RequestBody CouseOrderDto input) {
        return CouseOrderService.Enroll(input);
    }

    /**
     * 取消课程
     */
    @RequestMapping(value = "/Cancel", method = RequestMethod.POST)
    public void Cancel(@RequestBody CouseOrderDto input) {
        CouseOrderService.Cancel(input);
    }

    /**
     * 支付宝支付
     */
    @RequestMapping(value = "/AliPay", method = RequestMethod.POST)
    public ResponseData<String> AliPay(@RequestBody CouseOrderDto input) {
        var result = CouseOrderService.AliPay(input);
        return ResponseData.GetResponseDataInstance(result, "成功", true);
    }

}
