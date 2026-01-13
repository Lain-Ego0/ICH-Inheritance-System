package com.example.web.dto;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.example.web.entity.OrderDet;
import com.example.web.tools.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 订单明细类
 */
@Data
public class OrderDetDto extends BaseDto {

    /**
     * 订单
     */
    @JsonProperty("OrderId")
    private Integer OrderId;

    /**
     * 文创品
     */
    @JsonProperty("GoodId")
    private Integer GoodId;

    /**
     * 数量
     */
    @JsonProperty("Qty")
    private Integer Qty;

    /**
     * 删除购物车
     */
    @JsonProperty("BuyCardId")
    private Integer BuyCardId;

    /**
     * 购买价格
     */
    @JsonProperty("Price")
    private Double Price;

    @JsonProperty("OrderDto")
    private OrderInfoDto OrderDto;

    @JsonProperty("GoodDto")
    private GoodDto GoodDto;

    /**
     * 把订单明细传输模型转换成订单明细实体
     */
    public OrderDet MapToEntity() throws InvocationTargetException, IllegalAccessException {
        OrderDet OrderDet = new OrderDet();
        BeanUtils.copyProperties(OrderDet, this);
        return OrderDet;
    }

}
