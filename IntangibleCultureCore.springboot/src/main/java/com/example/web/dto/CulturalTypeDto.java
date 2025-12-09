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
 * 非遗文化类别类
 */
@Data
public class CulturalTypeDto extends BaseDto
{

    
     
    /**
     * 名称
     */ 
    @JsonProperty("Name")
    private String Name;

 	 /**
     * 把非遗文化类别传输模型转换成非遗文化类别实体
     */
    public CulturalType MapToEntity() throws InvocationTargetException, IllegalAccessException {
        CulturalType CulturalType= new CulturalType();
     
         BeanUtils.copyProperties(CulturalType,this);
        
        return CulturalType;
    }

}
