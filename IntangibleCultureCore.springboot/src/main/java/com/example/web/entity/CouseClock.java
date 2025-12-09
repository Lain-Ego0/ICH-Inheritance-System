package com.example.web.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.web.dto.CouseClockDto;
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
 * 课程打卡表
 */
@Data
@TableName("`CouseClock`")
public class CouseClock extends BaseEntity {

      
    /**
     * 所属课程
     */  
    @JsonProperty("CouseId")
    @TableField(value="CouseId",updateStrategy = FieldStrategy.IGNORED)
    private Integer CouseId;          
      
    /**
     * 打卡时间
     */  
    @JsonProperty("ClockTime")
    @TableField(value="ClockTime",updateStrategy = FieldStrategy.IGNORED)
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime ClockTime;             
      
    /**
     * 打卡章节
     */  
    @JsonProperty("CouseStepId")
    @TableField(value="CouseStepId",updateStrategy = FieldStrategy.IGNORED)
    private Integer CouseStepId;          
      
    /**
     * 用户
     */  
    @JsonProperty("UserId")
    @TableField(value="UserId",updateStrategy = FieldStrategy.IGNORED)
    private Integer UserId;          
  
    /**
     * 把课程打卡实体转换成课程打卡传输模型
     */
    public CouseClockDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        CouseClockDto CouseClockDto = new CouseClockDto();
        BeanUtils.copyProperties(CouseClockDto,this);
        return CouseClockDto;
    }

}
