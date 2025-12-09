package com.example.web.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.poi.hpsf.Decimal;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.BeanUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.sql.Date;
import java.sql.Timestamp;
import lombok.Data;
import java.time.LocalDateTime;
import com.example.web.dto.*;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
/**
 * 视频展览类型表
 */
@Data
@TableName("`VideoExhibitionType`")
public class VideoExhibitionType extends BaseEntity {

      
  	  /**
     * 分类
     */  
    @JsonProperty("Name")
    @TableField(value="Name",updateStrategy = FieldStrategy.IGNORED)
    private String Name;
  
    /**
     * 把视频展览类型实体转换成视频展览类型传输模型
     */
    public VideoExhibitionTypeDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        VideoExhibitionTypeDto VideoExhibitionTypeDto = new VideoExhibitionTypeDto();
       
        BeanUtils.copyProperties(VideoExhibitionTypeDto,this);
       
        return VideoExhibitionTypeDto;
    }

}
