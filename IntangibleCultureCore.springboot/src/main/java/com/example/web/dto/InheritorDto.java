package com.example.web.dto;
import com.example.web.enums.*;
import com.example.web.tools.dto.BaseDto;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.sql.Date;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.example.web.entity.*;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
/**
 * 传人类
 */
@Data
public class InheritorDto extends BaseDto
{

    
     
    /**
     * 姓名
     */ 
    @JsonProperty("Name")
    private String Name;
    
     
    /**
     * 头像
     */ 
    @JsonProperty("ImageUrls")
    private String ImageUrls;
    
     
    /**
     * 内容
     */ 
    @JsonProperty("Content")
    private String Content;
    
     
    /**
     * 性别
     */ 
    @JsonProperty("Sex")
    private String Sex;
    
     
    /**
     * 民族
     */ 
    @JsonProperty("Nation")
    private String Nation;
    
     
    /**
     * 编号
     */ 
    @JsonProperty("No")
    private String No;
    
     
    /**
     * 出生年月
     */ 
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("BirthDate")
    private LocalDateTime BirthDate;             
    
     
    /**
     * 省市区
     */ 
    @JsonProperty("ProviceCityArea")
    private String ProviceCityArea;

 	 /**
     * 把传人传输模型转换成传人实体
     */
    public Inheritor MapToEntity() throws InvocationTargetException, IllegalAccessException {
        Inheritor Inheritor= new Inheritor();
     
         BeanUtils.copyProperties(Inheritor,this);
        
        return Inheritor;
    }

}
