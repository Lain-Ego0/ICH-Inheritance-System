package com.example.web.dto;

import com.example.web.entity.CouseStudySchedule;
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
 * 课程学习进度类
 */
@Data
public class CouseStudyScheduleDto extends BaseDto
{

    
     
    /**
     * 学习人
     */ 
    @JsonProperty("StudyUserId")
    private Integer StudyUserId;          
    
     
    /**
     * 所属章节
     */ 
    @JsonProperty("CouseStepId")
    private Integer CouseStepId;          
    
     
    /**
     * 所属课程
     */ 
    @JsonProperty("CouseId")
    private Integer CouseId;          
    
     
    /**
     * 学习时长
     */ 
    @JsonProperty("Durtion")
    private Double Durtion;      
    
     
    /**
     * 开始时间
     */ 
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("BeginTime")
    private LocalDateTime BeginTime;             
    
     
    /**
     * 最后上传时间
     */ 
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("LastUploadTime")
    private LocalDateTime LastUploadTime;             

     @JsonProperty("StudyUserDto") 
    private AppUserDto StudyUserDto;                        
   
     @JsonProperty("CouseDto") 
    private CouseDto CouseDto;                        
   
     @JsonProperty("CouseStepDto") 
    private CouseStepDto CouseStepDto;                        
   
 	 /**
     * 把课程学习进度传输模型转换成课程学习进度实体
     */
    public CouseStudySchedule MapToEntity() throws InvocationTargetException, IllegalAccessException {
        CouseStudySchedule CouseStudySchedule= new CouseStudySchedule();
        BeanUtils.copyProperties(CouseStudySchedule,this);
        return CouseStudySchedule;
    }

}
