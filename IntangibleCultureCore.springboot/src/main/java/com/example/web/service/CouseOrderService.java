package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.CouseOrderDto;
import com.example.web.dto.query.CouseOrderPagedInput;
import com.example.web.entity.CouseOrder;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;

/**
 * 课程订单功能的Service接口的定义清单
 */
public interface CouseOrderService extends IService<CouseOrder> {

    /**
     * 课程订单的分页查询方法接口定义
     */
    public PagedResult<CouseOrderDto> List(CouseOrderPagedInput input);

    /**
     * 课程订单的新增或者修改方法接口定义
     */
    public CouseOrderDto CreateOrEdit(CouseOrderDto input);

    /**
     * 获取课程订单信息
     */
    public CouseOrderDto Get(CouseOrderPagedInput input);

    /**
     * 课程订单删除
     */
    public void Delete(IdInput input);

    /**
     * 课程订单批量删除
     */
    public void BatchDelete(IdsInput input);

    /**
     * 报名
     */
    public CouseOrderDto Enroll(CouseOrderDto input);

    /**
     * 取消课程
     */
    public void Cancel(CouseOrderDto input);

    /**
     * 支付宝支付检测
     */
    public void CheckAliPayCallBack();

    /**
     * 支付宝支付
     */
    public String AliPay(CouseOrderDto input);
}
