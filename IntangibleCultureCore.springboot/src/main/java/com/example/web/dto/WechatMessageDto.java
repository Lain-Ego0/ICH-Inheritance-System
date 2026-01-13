package com.example.web.dto;
import com.example.web.enums.*;
import com.example.web.tools.dto.BaseDto;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.sql.Date;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.web.entity.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
/**
 * 会话消息类
 */
@Data
public class WechatMessageDto extends BaseDto
{

    
     
    /**
     * 自身用户
     */ 
    @JsonProperty("SelfUserId")
    private Integer SelfUserId;          
    
     
    /**
     * 对方用户
     */ 
    @JsonProperty("OtherUserId")
    private Integer OtherUserId;          
    
     
    /**
     * 消息内容
     */ 
    @JsonProperty("Content")
    private String Content;
    
     
    /**
     * 发送时间
     */ 
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("SendTime")
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime SendTime;             

     @JsonProperty("OtherUserDto") 
    private AppUserDto OtherUserDto;                        
   
     @JsonProperty("SelfUserDto") 
    private AppUserDto SelfUserDto;                        


 	 /**
     * 把会话消息传输模型转换成会话消息实体
     */
    public WechatMessage MapToEntity() throws InvocationTargetException, IllegalAccessException {
        WechatMessage WechatMessage= new WechatMessage();
        BeanUtils.copyProperties(WechatMessage,this);
        return WechatMessage;
    }

}
