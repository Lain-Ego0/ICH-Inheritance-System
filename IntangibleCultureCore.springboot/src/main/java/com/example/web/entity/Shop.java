package com.example.web.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.web.dto.ShopDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
/**
 * 店铺表
 */
@Data
@TableName("`Shop`")
public class Shop extends BaseEntity {

      
  	  /**
     * 店铺名称
     */  
    @JsonProperty("Name")
    @TableField(value="Name",updateStrategy = FieldStrategy.IGNORED)
    private String Name;

      
  	  /**
     * Logo
     */  
    @JsonProperty("LogoCover")
    @TableField(value="LogoCover",updateStrategy = FieldStrategy.IGNORED)
    private String LogoCover;
      
  	  /**
     * 店铺地址
     */  
    @JsonProperty("Address")
    @TableField(value="Address",updateStrategy = FieldStrategy.IGNORED)
    private String Address;
      
    /**
     * 商家
     */  
    @JsonProperty("UserId")
    @TableField(value="UserId",updateStrategy = FieldStrategy.IGNORED)
    private Integer UserId;          

    /**
     * 审核状态
     */  
    @JsonProperty("AuditStatus")
    @TableField(value="AuditStatus",updateStrategy = FieldStrategy.IGNORED)
    private Integer AuditStatus;          
      
    /**
     * 审核人
     */  
    @JsonProperty("AuditUserId")
    @TableField(value="AuditUserId",updateStrategy = FieldStrategy.IGNORED)
    private Integer AuditUserId;          
      
  	  /**
     * 审核原因
     */  
    @JsonProperty("AuditReason")
    @TableField(value="AuditReason",updateStrategy = FieldStrategy.IGNORED)
    private String AuditReason;
      
    /**
     * 店铺详情
     */  
    @JsonProperty("Content")
     @TableField(value="Content",updateStrategy = FieldStrategy.IGNORED)
    private String Content;
      
  	  /**
     * 店铺保障
     */  
    @JsonProperty("Ensure")
    @TableField(value="Ensure",updateStrategy = FieldStrategy.IGNORED)
    private String Ensure;


    /**
     * 店铺电话
     */
    @JsonProperty("Phone")
    @TableField(value="Phone",updateStrategy = FieldStrategy.IGNORED)
    private String Phone;


    /**
     * 店铺邮箱
     */
    @JsonProperty("Email")
    @TableField(value="Email",updateStrategy = FieldStrategy.IGNORED)
    private String Email;
  
    /**
     * 把店铺实体转换成店铺传输模型
     */
    public ShopDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        ShopDto ShopDto = new ShopDto();
        BeanUtils.copyProperties(ShopDto,this);
        return ShopDto;
    }

}
