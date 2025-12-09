package com.example.web.dto;

import com.example.web.entity.OrderReturn;
import com.example.web.enums.OrderReturnTypeEnum;
import com.example.web.tools.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 订单退货类
 */
@Data
public class OrderReturnDto extends BaseDto {

    /**
     * 退款方式
     */
    @JsonProperty("OrderReturnType")
    private Integer OrderReturnType;

    @JsonProperty("OrderReturnTypeFormat")
    private String OrderReturnTypeFormat() {
        return OrderReturnTypeEnum.GetEnum(OrderReturnType).name();
    }

    /**
     * 订单
     */
    @JsonProperty("OrderId")
    private Integer OrderId;


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
     * 申请原因
     */
    @JsonProperty("ApplyReason")
    private String ApplyReason;


    /**
     * 用户拍照
     */
    @JsonProperty("UserImageUrls")
    private String UserImageUrls;


    /**
     * 物流单号
     */
    @JsonProperty("LogisticsNo")
    private String LogisticsNo;



    /**
     * 卖家回复
     */
    @JsonProperty("SellerReply")
    private String SellerReply;

    /**
     * 卖家拍照
     */
    @JsonProperty("SellerImageUrls")
    private String SellerImageUrls;

    /**
     * 卖家是否同意
     */
    @JsonProperty("IsAggren")
    private Boolean IsAggren;



    /**
     * 是否申请客服介入
     */
    @JsonProperty("IsApplyCustom")
    private Boolean IsApplyCustom;

    /**
     * 申请退款金额
     */
    @JsonProperty("ReturnMoney")
    private Double ReturnMoney;

    @JsonProperty("UserDto")
    private AppUserDto UserDto;

    @JsonProperty("OrderDto")
    private OrderInfoDto OrderDto;

    @JsonProperty("ShopUserDto")
    private AppUserDto ShopUserDto;

    @JsonProperty("IsDiscard")
    private Boolean IsDiscard;

    /**
     * 把订单退货传输模型转换成订单退货实体
     */
    public OrderReturn MapToEntity() throws InvocationTargetException, IllegalAccessException {
        OrderReturn OrderReturn = new OrderReturn();
        BeanUtils.copyProperties(OrderReturn, this);
        return OrderReturn;
    }

}
