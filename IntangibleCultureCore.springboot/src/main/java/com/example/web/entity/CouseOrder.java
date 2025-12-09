package com.example.web.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.web.dto.CouseOrderDto;
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
 * 课程订单表
 */
@Data
@TableName("`CouseOrder`")
public class CouseOrder extends BaseEntity {

    /**
     * 课程
     */
    @JsonProperty("CouseId")
    @TableField(value = "CouseId", updateStrategy = FieldStrategy.IGNORED)
    private Integer CouseId;

    /**
     * 订单编号
     */
    @JsonProperty("No")
    @TableField(value = "No", updateStrategy = FieldStrategy.IGNORED)
    private String No;

    /**
     * 支付金额
     */
    @JsonProperty("PayMoney")
    @TableField(value = "PayMoney", updateStrategy = FieldStrategy.IGNORED)
    private Double PayMoney;

    /**
     * 支付类型
     */
    @JsonProperty("PayType")
    @TableField(value = "PayType", updateStrategy = FieldStrategy.IGNORED)
    private String PayType;

    /**
     * 支付时间
     */
    @JsonProperty("PayTime")
    @TableField(value = "PayTime", updateStrategy = FieldStrategy.IGNORED)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime PayTime;

    /**
     * 订单状态
     */
    @JsonProperty("OrderStatus")
    @TableField(value = "OrderStatus", updateStrategy = FieldStrategy.IGNORED)
    private Integer OrderStatus;

    /**
     * 下单人
     */
    @JsonProperty("UserId")
    @TableField(value = "UserId", updateStrategy = FieldStrategy.IGNORED)
    private Integer UserId;

    /**
     * 是否退款
     */
    @JsonProperty("IsReturn")
    @TableField(value = "IsReturn", updateStrategy = FieldStrategy.IGNORED)
    private Boolean IsReturn;

    /**
     * 退款时间
     */
    @JsonProperty("ReturnTime")
    @TableField(value = "ReturnTime", updateStrategy = FieldStrategy.IGNORED)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime ReturnTime;

    /**
     * 评价
     */
    @JsonProperty("Comment")
    @TableField(value = "Comment", updateStrategy = FieldStrategy.IGNORED)
    private String Comment;

    /**
     * 评分
     */
    @JsonProperty("CommentScore")
    @TableField(value = "CommentScore", updateStrategy = FieldStrategy.IGNORED)
    private Double CommentScore;

    /**
     * 把课程订单实体转换成课程订单传输模型
     */
    public CouseOrderDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        CouseOrderDto CouseOrderDto = new CouseOrderDto();

        BeanUtils.copyProperties(CouseOrderDto, this);

        return CouseOrderDto;
    }

}
