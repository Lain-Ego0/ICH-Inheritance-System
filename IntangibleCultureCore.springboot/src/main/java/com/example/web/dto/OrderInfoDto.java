package com.example.web.dto;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.example.web.entity.OrderInfo;
import com.example.web.enums.OrderStatusEnum;
import com.example.web.tools.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Data;

/**
 * 订单类
 */
@Data
public class OrderInfoDto extends BaseDto {

    /**
     * 订单编号
     */
    @JsonProperty("OrderNo")
    private String OrderNo;

    /**
     * 用户
     */
    @JsonProperty("UserId")
    private Integer UserId;

    /**
     * 选中的优惠卷
     */
    @JsonProperty("CouponRecordIds")
    private List<Integer> CouponRecordIds;
    /**
     * 商家
     */
    @JsonProperty("ShopUserId")
    private Integer ShopUserId;

    /**
     * 发货时间
     */
    @JsonProperty("DeliveryTime")
    private LocalDateTime DeliveryTime;

    /**
     * 收货时间
     */
    @JsonProperty("GetOrderTime")
    private LocalDateTime GetOrderTime;

    /**
     * 店铺
     */
    @JsonProperty("ShopId")
    private Integer ShopId;

    /**
     * 总数量
     */
    @JsonProperty("TotalQty")
    private Integer TotalQty;

    /**
     * 总金额
     */
    @JsonProperty("TotalMoney")
    private Double TotalMoney;

    /**
     * 原始金额
     */
    @JsonProperty("OrginMoney")
    private Double OrginMoney;

    /**
     * 折扣金额
     */
    @JsonProperty("DiscountMoney")
    private Double DiscountMoney;

    /**
     * 订单状态
     */
    @JsonProperty("OrderStatus")
    private Integer OrderStatus;

    @JsonProperty("OrderStatusFormat")
    private String OrderStatusFormat() {
        return OrderStatusEnum.GetEnum(OrderStatus).name();
    }

    /**
     * 支付类型
     */
    @JsonProperty("PayType")
    private String PayType;

    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("PayTime")
    private LocalDateTime PayTime;

    /**
     * 过期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("ExpireTime")
    private LocalDateTime ExpireTime;

    /**
     * 物流单号
     */
    @JsonProperty("LogisticsNo")
    private String LogisticsNo;

    /**
     * 收货地址
     */
    @JsonProperty("ReceiveAddress")
    private String ReceiveAddress;

    /**
     * 收货电话
     */
    @JsonProperty("ReceivePhone")
    private String ReceivePhone;

    /**
     * 支付订单号
     */
    @JsonProperty("PayOrderNo")
    private String PayOrderNo;

    /**
     * 收货人
     */
    @JsonProperty("ReceiveName")
    private String ReceiveName;

    @JsonProperty("ShopUserDto")
    private AppUserDto ShopUserDto;

    @JsonProperty("UserDto")
    private AppUserDto UserDto;

    @JsonProperty("ShopDto")
    private ShopDto ShopDto;

    @JsonProperty("OrderDets")
    private List<OrderDetDto> OrderDets;

    @JsonProperty("OrderComments")
    private List<OrderCommentDto> OrderComments;

    @JsonProperty("OrderReturns")
    private List<OrderReturnDto> OrderReturns;

    /**
     * 备注
     */
    @JsonProperty("Remark")
    private String Remark;

    /**
     * 回调接口
     */
    @JsonProperty("CallBack")
    private String CallBack;

    /**
     * 物流信息
     */
    @JsonProperty("ExpressInfo")
    private String ExpressInfo;

    /**
     * 是否自提
     */
    @JsonProperty("IsSelfPickup")
    private Boolean IsSelfPickup;

    /**
     * 自提时间
     */
    @JsonProperty("SelfPickupTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime SelfPickupTime;

    /**
     * 把订单传输模型转换成订单实体
     */
    public OrderInfo MapToEntity() throws InvocationTargetException, IllegalAccessException {
        OrderInfo OrderInfo = new OrderInfo();
        BeanUtils.copyProperties(OrderInfo, this);
        return OrderInfo;
    }

}
