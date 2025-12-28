package com.example.web.dto;

import com.example.web.entity.UserAddress;
import com.example.web.tools.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 收货地址类
 */
@Data
public class UserAddressDto extends BaseDto
{

    
     
    /**
     * 收货姓名
     */ 
    @JsonProperty("Name")
    private String Name;
    
     
    /**
     * 电话
     */ 
    @JsonProperty("Phone")
    private String Phone;

    /**
     * 省市区
     */
    @JsonProperty("ProviceCityArea")
    private String ProviceCityArea;




    /**
     * 详细地址
     */
    @JsonProperty("Address")
    private String Address;
     
    /**
     * 所属用户
     */ 
    @JsonProperty("BelongUserId")
    private Integer BelongUserId;          
    
     
    /**
     * 性别
     */ 
    @JsonProperty("Sex")
    private String Sex;
    
     
    /**
     * 门牌号
     */ 
    @JsonProperty("HouseNumber")
    private String HouseNumber;

     @JsonProperty("BelongUserDto") 
    private AppUserDto BelongUserDto;                        
   
 	 /**
     * 把收货地址传输模型转换成收货地址实体
     */
    public UserAddress MapToEntity() throws InvocationTargetException, IllegalAccessException {
        UserAddress UserAddress= new UserAddress();
        BeanUtils.copyProperties(UserAddress,this);
        return UserAddress;
    }

}
