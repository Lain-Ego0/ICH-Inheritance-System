package com.example.web.dto;

import com.example.web.entity.CouseOrder;
import com.example.web.enums.CouseOrderStatusEnum;
import com.example.web.tools.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

/**
 * 课程订单类
 */
@Data
public class CouseOrderDto extends BaseDto {

    /**
     * 课程
     */
    @JsonProperty("CouseId")
    private Integer CouseId;

    /**
     * 订单编号
     */
    @JsonProperty("No")
    private String No;

    /**
     * 支付金额
     */
    @JsonProperty("PayMoney")
    private Double PayMoney;

    /**
     * 支付类型
     */
    @JsonProperty("PayType")
    private String PayType;

    /**
     * 下单人
     */
    @JsonProperty("UserId")
    private Integer UserId;

    /**
     * 是否退款
     */
    @JsonProperty("IsReturn")
    private Boolean IsReturn;

    /**
     * 退款时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("ReturnTime")
    private LocalDateTime ReturnTime;

    /**
     * 评价
     */
    @JsonProperty("Comment")
    private String Comment;

    /**
     * 评分
     */
    @JsonProperty("CommentScore")
    private Double CommentScore;

    @JsonProperty("UserDto")
    private AppUserDto UserDto;

    @JsonProperty("CouseDto")
    private CouseDto CouseDto;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("PayTime")
    private LocalDateTime PayTime;

    @JsonProperty("OrderStatus")
    private Integer OrderStatus;

    /**
     * 审核状态描述
     * 
     * @return
     */
    @JsonProperty("OrderStatusFormat")
    public String OrderStatusFormat() {
        return CouseOrderStatusEnum.GetEnum(OrderStatus).toString();
    }

    /**
     * 回调地址
     */
    @JsonProperty("CallBack")
    private String CallBack;

    /**
     * 把课程订单传输模型转换成课程订单实体
     */
    public CouseOrder MapToEntity() throws InvocationTargetException, IllegalAccessException {
        CouseOrder CouseOrder = new CouseOrder();

        BeanUtils.copyProperties(CouseOrder, this);

        return CouseOrder;
    }

}
