package com.example.web.dto;

import com.example.web.entity.CouseClock;
import com.example.web.tools.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * 课程打卡类
 */
@Data
public class CouseClockDto extends BaseDto
{

    
     
    /**
     * 所属课程
     */ 
    @JsonProperty("CouseId")
    private Integer CouseId;          
    
     
    /**
     * 打卡时间
     */ 
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("ClockTime")
    private LocalDateTime ClockTime;             
    
     
    /**
     * 打卡章节
     */ 
    @JsonProperty("CouseStepId")
    private Integer CouseStepId;          
    
     
    /**
     * 用户
     */ 
    @JsonProperty("UserId")
    private Integer UserId;          

     @JsonProperty("CouseDto") 
    private CouseDto CouseDto;                        
   
     @JsonProperty("CouseStepDto") 
    private CouseStepDto CouseStepDto;                        
   
     @JsonProperty("UserDto") 
    private AppUserDto UserDto;                        
   
 	 /**
     * 把课程打卡传输模型转换成课程打卡实体
     */
    public CouseClock MapToEntity() throws InvocationTargetException, IllegalAccessException {
        CouseClock CouseClock= new CouseClock();
        BeanUtils.copyProperties(CouseClock,this);
        return CouseClock;
    }

}
