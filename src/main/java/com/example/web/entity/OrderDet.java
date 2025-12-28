package com.example.web.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.web.dto.OrderDetDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 订单明细表
 */
@Data
@TableName("`OrderDet`")
public class OrderDet extends BaseEntity {

    /**
     * 订单
     */
    @JsonProperty("OrderId")
    @TableField(value = "OrderId", updateStrategy = FieldStrategy.IGNORED)
    private Integer OrderId;

    /**
     * 文创品
     */
    @JsonProperty("GoodId")
    @TableField(value = "GoodId", updateStrategy = FieldStrategy.IGNORED)
    private Integer GoodId;

    /**
     * 数量
     */
    @JsonProperty("Qty")
    @TableField(value = "Qty", updateStrategy = FieldStrategy.IGNORED)
    private Integer Qty;

    /**
     * 购买价格
     */
    @JsonProperty("Price")
    @TableField(value = "Price", updateStrategy = FieldStrategy.IGNORED)
    private Double Price;

    /**
     * 把订单明细实体转换成订单明细传输模型
     */
    public OrderDetDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        OrderDetDto OrderDetDto = new OrderDetDto();
        BeanUtils.copyProperties(OrderDetDto, this);
        return OrderDetDto;
    }

}
