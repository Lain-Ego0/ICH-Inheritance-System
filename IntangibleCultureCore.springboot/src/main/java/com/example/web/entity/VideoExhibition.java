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
 * 视频展览表
 */
@Data
@TableName("`VideoExhibition`")
public class VideoExhibition extends BaseEntity {

      
    /**
     * 分类
     */  
    @JsonProperty("VideoExhibitionTypeId")
    @TableField(value="VideoExhibitionTypeId",updateStrategy = FieldStrategy.IGNORED)
    private Integer VideoExhibitionTypeId;          
      
  	  /**
     * 视频
     */  
    @JsonProperty("VideoUrl")
    @TableField(value="VideoUrl",updateStrategy = FieldStrategy.IGNORED)
    private String VideoUrl;
      
  	  /**
     * 视频封面
     */  
    @JsonProperty("VideoCover")
    @TableField(value="VideoCover",updateStrategy = FieldStrategy.IGNORED)
    private String VideoCover;
      
  	  /**
     * 短描述
     */  
    @JsonProperty("ShortDesc")
    @TableField(value="ShortDesc",updateStrategy = FieldStrategy.IGNORED)
    private String ShortDesc;
      
    /**
     * 是否上架
     */  
    @JsonProperty("IsPutaway")
    @TableField(value="IsPutaway",updateStrategy = FieldStrategy.IGNORED)
    private Boolean IsPutaway;          
      
  	  /**
     * 省市区
     */  
    @JsonProperty("ProviceCityArea")
    @TableField(value="ProviceCityArea",updateStrategy = FieldStrategy.IGNORED)
    private String ProviceCityArea;
  
    /**
     * 把视频展览实体转换成视频展览传输模型
     */
    public VideoExhibitionDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        VideoExhibitionDto VideoExhibitionDto = new VideoExhibitionDto();
       
        BeanUtils.copyProperties(VideoExhibitionDto,this);
       
        return VideoExhibitionDto;
    }

}
