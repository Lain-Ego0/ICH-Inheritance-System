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
 * 视频展览收藏类
 */
@Data
public class VideoExhibitionCollectDto extends BaseDto
{

    
     
    /**
     * 收藏人
     */ 
    @JsonProperty("CoolectUserId")
    private Integer CoolectUserId;          
    
     
    /**
     * 视频展览
     */ 
    @JsonProperty("VideoExhibitionId")
    private Integer VideoExhibitionId;          

     @JsonProperty("CoolectUserDto") 
    private AppUserDto CoolectUserDto;                        
   
     @JsonProperty("VideoExhibitionDto") 
    private VideoExhibitionDto VideoExhibitionDto;                        
   
 	 /**
     * 把视频展览收藏传输模型转换成视频展览收藏实体
     */
    public VideoExhibitionCollect MapToEntity() throws InvocationTargetException, IllegalAccessException {
        VideoExhibitionCollect VideoExhibitionCollect= new VideoExhibitionCollect();
     
         BeanUtils.copyProperties(VideoExhibitionCollect,this);
        
        return VideoExhibitionCollect;
    }

}
