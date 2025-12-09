package com.example.web.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.poi.hpsf.Decimal;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.BeanUtils;
import java.sql.Date;
import java.sql.Timestamp;
import lombok.Data;
import java.time.LocalDateTime;
import com.example.web.dto.*;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
/**
 * 会话消息表
 */
@Data
@TableName("`WechatMessage`")
public class WechatMessage extends BaseEntity {

      
    /**
     * 自身用户
     */  
    @JsonProperty("SelfUserId")
    @TableField(value="SelfUserId",updateStrategy = FieldStrategy.IGNORED)
    private Integer SelfUserId;          
      
    /**
     * 对方用户
     */  
    @JsonProperty("OtherUserId")
    @TableField(value="OtherUserId",updateStrategy = FieldStrategy.IGNORED)
    private Integer OtherUserId;          
      
  	  /**
     * 消息内容
     */  
    @JsonProperty("Content")
    @TableField(value="Content",updateStrategy = FieldStrategy.IGNORED)
    private String Content;
      
    /**
     * 发送时间
     */  
    @JsonProperty("SendTime")
    @TableField(value="SendTime",updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime SendTime;             
  
    /**
     * 把会话消息实体转换成会话消息传输模型
     */
    public WechatMessageDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        WechatMessageDto WechatMessageDto = new WechatMessageDto();
        BeanUtils.copyProperties(WechatMessageDto,this);
        return WechatMessageDto;
    }

}
