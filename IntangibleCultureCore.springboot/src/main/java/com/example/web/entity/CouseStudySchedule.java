package com.example.web.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.web.dto.CouseStudyScheduleDto;
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
 * 课程学习进度表
 */
@Data
@TableName("`CouseStudySchedule`")
public class CouseStudySchedule extends BaseEntity {

      
    /**
     * 学习人
     */  
    @JsonProperty("StudyUserId")
    @TableField(value="StudyUserId",updateStrategy = FieldStrategy.IGNORED)
    private Integer StudyUserId;          
      
    /**
     * 所属章节
     */  
    @JsonProperty("CouseStepId")
    @TableField(value="CouseStepId",updateStrategy = FieldStrategy.IGNORED)
    private Integer CouseStepId;          
      
    /**
     * 所属课程
     */  
    @JsonProperty("CouseId")
    @TableField(value="CouseId",updateStrategy = FieldStrategy.IGNORED)
    private Integer CouseId;          
      
    /**
     * 学习时长
     */  
    @JsonProperty("Durtion")
    @TableField(value="Durtion",updateStrategy = FieldStrategy.IGNORED)
    private Double Durtion;      
      
    /**
     * 开始时间
     */  
    @JsonProperty("BeginTime")
    @TableField(value="BeginTime",updateStrategy = FieldStrategy.IGNORED)
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime BeginTime;             
      
    /**
     * 最后上传时间
     */  
    @JsonProperty("LastUploadTime")
    @TableField(value="LastUploadTime",updateStrategy = FieldStrategy.IGNORED)
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime LastUploadTime;             
  
    /**
     * 把课程学习进度实体转换成课程学习进度传输模型
     */
    public CouseStudyScheduleDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        CouseStudyScheduleDto CouseStudyScheduleDto = new CouseStudyScheduleDto();
        BeanUtils.copyProperties(CouseStudyScheduleDto,this);
        return CouseStudyScheduleDto;
    }

}
