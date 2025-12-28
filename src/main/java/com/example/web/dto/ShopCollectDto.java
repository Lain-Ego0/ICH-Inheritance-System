package com.example.web.dto;

import com.example.web.entity.ShopCollect;
import com.example.web.tools.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 店铺收藏类
 */
@Data
public class ShopCollectDto extends BaseDto
{

    
    /**
     * 店铺
     */ 
    @JsonProperty("ShopId")
    private Integer ShopId;          

    /**
     * 收藏人
     */ 
    @JsonProperty("CollectUserId")
    private Integer CollectUserId;          

     @JsonProperty("ShopDto") 
    private ShopDto ShopDto;                        
   
     @JsonProperty("CollectUserDto") 
    private AppUserDto CollectUserDto;                        
   
 	 /**
     * 把店铺收藏传输模型转换成店铺收藏实体
     */
    public ShopCollect MapToEntity() throws InvocationTargetException, IllegalAccessException {
        ShopCollect ShopCollect= new ShopCollect();
        BeanUtils.copyProperties(ShopCollect,this);
        return ShopCollect;
    }

}
