package com.example.web.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.web.dto.UserAddressDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
/**
 * 收货地址表
 */
@Data
@TableName("`UserAddress`")
public class UserAddress extends BaseEntity {

      
  	  /**
     * 收货姓名
     */  
    @JsonProperty("Name")
    @TableField(value="Name",updateStrategy = FieldStrategy.IGNORED)
    private String Name;
      
  	  /**
     * 电话
     */  
    @JsonProperty("Phone")
    @TableField(value="Phone",updateStrategy = FieldStrategy.IGNORED)
    private String Phone;

    /**
     * 省市区
     */
    @JsonProperty("ProviceCityArea")
    @TableField(value="ProviceCityArea",updateStrategy = FieldStrategy.IGNORED)
    private String ProviceCityArea;
    /**
     * 所属用户
     */  
    @JsonProperty("BelongUserId")
    @TableField(value="BelongUserId",updateStrategy = FieldStrategy.IGNORED)
    private Integer BelongUserId;          
      
  	  /**
     * 性别
     */  
    @JsonProperty("Sex")
    @TableField(value="Sex",updateStrategy = FieldStrategy.IGNORED)
    private String Sex;
      
  	  /**
     * 门牌号
     */  
    @JsonProperty("HouseNumber")
    @TableField(value="HouseNumber",updateStrategy = FieldStrategy.IGNORED)
    private String HouseNumber;

    /**
     * 详细地址
     */
    @JsonProperty("Address")
    @TableField(value="Address",updateStrategy = FieldStrategy.IGNORED)
    private String Address;
  
    /**
     * 把收货地址实体转换成收货地址传输模型
     */
    public UserAddressDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        UserAddressDto UserAddressDto = new UserAddressDto();
        BeanUtils.copyProperties(UserAddressDto,this);
        return UserAddressDto;
    }

}
