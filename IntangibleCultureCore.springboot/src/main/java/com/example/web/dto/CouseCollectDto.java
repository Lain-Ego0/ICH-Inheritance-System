package com.example.web.dto;

import com.example.web.entity.CouseCollect;
import com.example.web.tools.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 课程收藏类
 */
@Data
public class CouseCollectDto extends BaseDto
{

    
     
    /**
     * 课程
     */ 
    @JsonProperty("CouseId")
    private Integer CouseId;          
    
     
    /**
     * 收藏人
     */ 
    @JsonProperty("CollectUserId")
    private Integer CollectUserId;          

     @JsonProperty("CollectUserDto") 
    private AppUserDto CollectUserDto;                        
   
     @JsonProperty("CouseDto") 
    private CouseDto CouseDto;                        
   
 	 /**
     * 把课程收藏传输模型转换成课程收藏实体
     */
    public CouseCollect MapToEntity() throws InvocationTargetException, IllegalAccessException {
        CouseCollect CouseCollect= new CouseCollect();
     
         BeanUtils.copyProperties(CouseCollect,this);
        
        return CouseCollect;
    }

}
