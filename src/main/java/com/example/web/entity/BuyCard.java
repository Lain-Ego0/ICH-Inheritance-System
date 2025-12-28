package com.example.web.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.web.dto.BuyCardDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 购物车表
 */
@Data
@TableName("`BuyCard`")
public class BuyCard extends BaseEntity {

    /**
     * 店铺
     */
    @JsonProperty("ShopId")
    @TableField(value = "ShopId", updateStrategy = FieldStrategy.IGNORED)
    private Integer ShopId;

    /**
     * 所属用户
     */
    @JsonProperty("BelongUserId")
    @TableField(value = "BelongUserId", updateStrategy = FieldStrategy.IGNORED)
    private Integer BelongUserId;

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
     * 把购物车实体转换成购物车传输模型
     */
    public BuyCardDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        BuyCardDto BuyCardDto = new BuyCardDto();
        BeanUtils.copyProperties(BuyCardDto, this);
        return BuyCardDto;
    }

}
