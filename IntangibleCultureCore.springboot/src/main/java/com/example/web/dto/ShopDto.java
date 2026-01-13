package com.example.web.dto;

import com.example.web.entity.Shop;
import com.example.web.enums.ShopAuditStatusEnum;
import com.example.web.tools.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 店铺类
 */
@Data
public class ShopDto extends BaseDto {


    /**
     * 店铺名称
     */
    @JsonProperty("Name")
    private String Name;


    /**
     * Logo
     */
    @JsonProperty("LogoCover")
    private String LogoCover;


    /**
     * 店铺地址
     */
    @JsonProperty("Address")
    private String Address;


    /**
     * 商家
     */
    @JsonProperty("UserId")
    private Integer UserId;


    /**
     * 审核状态
     */
    @JsonProperty("AuditStatus")
    private Integer AuditStatus;

     public String getAuditStatusFormat() {
        return ShopAuditStatusEnum.GetEnum(AuditStatus).toString();
    }

    private String AuditStatusFormat;


    /**
     * 审核人
     */
    @JsonProperty("AuditUserId")
    private Integer AuditUserId;


    /**
     * 审核原因
     */
    @JsonProperty("AuditReason")
    private String AuditReason;


    /**
     * 店铺详情
     */
    @JsonProperty("Content")
    private String Content;

    /**
     * 电话号码
     */
    @JsonProperty("Phone")
    private String Phone;



    /**
     * 店铺邮箱
     */
    @JsonProperty("Email")
    private String Email;
    /**
     * 店铺保障
     */
    @JsonProperty("Ensure")
    private String Ensure;

    @JsonProperty("UserDto")
    private AppUserDto UserDto;

    @JsonProperty("AuditUserDto")
    private AppUserDto AuditUserDto;

    @JsonProperty("UserName")
    private String UserName;

    /**
     * 把店铺传输模型转换成店铺实体
     */
    public Shop MapToEntity() throws InvocationTargetException, IllegalAccessException {
        Shop Shop = new Shop();
        BeanUtils.copyProperties(Shop, this);
        return Shop;
    }

}
