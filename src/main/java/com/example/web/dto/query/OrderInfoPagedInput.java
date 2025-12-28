package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * OrderInfo查询模型
 */
@NoArgsConstructor
@Data
public class OrderInfoPagedInput extends PagedInput {

    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 订单编号模糊查询条件
     */
    @JsonProperty("OrderNoLike")
    private String OrderNoLike;
    /**
     * 支付类型模糊查询条件
     */
    @JsonProperty("PayTypeLike")
    private String PayTypeLike;
    /**
     * 物流单号模糊查询条件
     */
    @JsonProperty("LogisticsNoLike")
    private String LogisticsNoLike;
    /**
     * 收货地址模糊查询条件
     */
    @JsonProperty("ReceiveAddressLike")
    private String ReceiveAddressLike;
    /**
     * 收货电话模糊查询条件
     */
    @JsonProperty("ReceivePhoneLike")
    private String ReceivePhoneLike;
    /**
     * 收货人模糊查询条件
     */
    @JsonProperty("ReceiveNameLike")
    private String ReceiveNameLike;

    /**
     * 支付订单号
     */
    @JsonProperty("PayOrderNo")
    private String PayOrderNo;
    /**
     * 用户
     */
    @JsonProperty("UserId")
    private Integer UserId;
    /**
     * 商家
     */
    @JsonProperty("ShopUserId")
    private Integer ShopUserId;
    /**
     * 店铺
     */
    @JsonProperty("ShopId")
    private Integer ShopId;
    /**
     * 支付时间时间范围
     */
    @JsonProperty("PayTimeRange")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> PayTimeRange;
    /**
     * 过期时间时间范围
     */
    @JsonProperty("ExpireTimeRange")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> ExpireTimeRange;

    /**
     * 物流单号
     */
    @JsonProperty("LogisticsNo")
    private String LogisticsNo;

    /**
     * 订单状态集合
     */
    @JsonProperty("OrderStatusList")
    private List<Integer> OrderStatusList;

}
