package com.example.web.job;

import com.example.web.service.CouseOrderService;
import com.example.web.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderInfoJob {
    @Autowired()
    private OrderInfoService OrderService;

    @Autowired()
    private com.example.web.service.CouseOrderService CouseOrderService;

    /**
     * 每隔5s查询支付状态
     */
    @Scheduled(cron = "*/5 * * * * ?")
    private void AutoCheckAliPayCallBack() {
        long nowDateTime = System.currentTimeMillis();

        OrderService.CheckAliPayCallBack();
    }
    /**
     * 每隔5s查询是否订单逾期了
     */
    @Scheduled(cron = "*/5 * * * * ?")
    private void CheckExpire() {
        long nowDateTime = System.currentTimeMillis();

        OrderService.CheckExpire();
    }
    /**
     * 每隔5s查询是否自动逾期
     */
    @Scheduled(cron = "*/5 * * * * ?")
    private void AutoOverdueTimes() {
        long nowDateTime = System.currentTimeMillis();

        CouseOrderService.CheckAliPayCallBack();

    }
}
