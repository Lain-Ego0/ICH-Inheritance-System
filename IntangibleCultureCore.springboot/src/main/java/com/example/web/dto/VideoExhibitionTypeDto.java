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
 * 视频展览类型类
 */
@Data
public class VideoExhibitionTypeDto extends BaseDto
{

    
     
    /**
     * 分类
     */ 
    @JsonProperty("Name")
    private String Name;

 	 /**
     * 把视频展览类型传输模型转换成视频展览类型实体
     */
    public VideoExhibitionType MapToEntity() throws InvocationTargetException, IllegalAccessException {
        VideoExhibitionType VideoExhibitionType= new VideoExhibitionType();
     
         BeanUtils.copyProperties(VideoExhibitionType,this);
        
        return VideoExhibitionType;
    }

}
