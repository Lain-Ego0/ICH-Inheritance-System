package com.example.web.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.web.dto.OrderReturnDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 订单退货表
 */
@Data
@TableName("`OrderReturn`")
public class OrderReturn extends BaseEntity {

    /**
     * 退款方式
     */
    @JsonProperty("OrderReturnType")
    @TableField(value = "OrderReturnType", updateStrategy = FieldStrategy.IGNORED)
    private Integer OrderReturnType;


    /**
     * 订单
     */
    @JsonProperty("OrderId")
    @TableField(value = "OrderId", updateStrategy = FieldStrategy.IGNORED)
    private Integer OrderId;

    /**
     * 用户
     */
    @JsonProperty("UserId")
    @TableField(value = "UserId", updateStrategy = FieldStrategy.IGNORED)
    private Integer UserId;

    /**
     * 商家
     */
    @JsonProperty("ShopUserId")
    @TableField(value = "ShopUserId", updateStrategy = FieldStrategy.IGNORED)
    private Integer ShopUserId;

    /**
     * 申请原因
     */
    @JsonProperty("ApplyReason")
    @TableField(value = "ApplyReason", updateStrategy = FieldStrategy.IGNORED)
    private String ApplyReason;

    /**
     * 用户拍照
     */
    @JsonProperty("UserImageUrls")
    @TableField(value = "UserImageUrls", updateStrategy = FieldStrategy.IGNORED)
    private String UserImageUrls;

    /**
     * 申请退款金额
     */
    @JsonProperty("ReturnMoney")
    @TableField(value = "ReturnMoney", updateStrategy = FieldStrategy.IGNORED)
    private Double ReturnMoney;


    /**
     * 物流单号
     */
    @JsonProperty("LogisticsNo")
    @TableField(value = "LogisticsNo", updateStrategy = FieldStrategy.IGNORED)
    private String LogisticsNo;



    /**
     * 卖家回复
     */
    @JsonProperty("SellerReply")
    @TableField(value = "SellerReply", updateStrategy = FieldStrategy.IGNORED)
    private String SellerReply;

    /**
     * 卖家拍照
     */
    @JsonProperty("SellerImageUrls")
    @TableField(value = "SellerImageUrls", updateStrategy = FieldStrategy.IGNORED)
    private String SellerImageUrls;

    /**
     * 卖家是否同意
     */
    @JsonProperty("IsAggren")
    @TableField(value = "IsAggren", updateStrategy = FieldStrategy.IGNORED)
    private Boolean IsAggren;

    /**
     * 是否申请客服介入
     */
    @JsonProperty("IsApplyCustom")
    @TableField(value = "IsApplyCustom", updateStrategy = FieldStrategy.IGNORED)
    private Boolean IsApplyCustom;


    /**
     * 本次申请是否失效
     */
    @JsonProperty("IsDiscard")
    @TableField(value = "IsDiscard", updateStrategy = FieldStrategy.IGNORED)
    private Boolean IsDiscard;
    /**
     * 把订单退货实体转换成订单退货传输模型
     */
    public OrderReturnDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        OrderReturnDto OrderReturnDto = new OrderReturnDto();
        BeanUtils.copyProperties(OrderReturnDto, this);
        return OrderReturnDto;
    }

}
