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
 * 视频展览收藏表
 */
@Data
@TableName("`VideoExhibitionCollect`")
public class VideoExhibitionCollect extends BaseEntity {

      
    /**
     * 收藏人
     */  
    @JsonProperty("CoolectUserId")
    @TableField(value="CoolectUserId",updateStrategy = FieldStrategy.IGNORED)
    private Integer CoolectUserId;          
      
    /**
     * 视频展览
     */  
    @JsonProperty("VideoExhibitionId")
    @TableField(value="VideoExhibitionId",updateStrategy = FieldStrategy.IGNORED)
    private Integer VideoExhibitionId;          
  
    /**
     * 把视频展览收藏实体转换成视频展览收藏传输模型
     */
    public VideoExhibitionCollectDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        VideoExhibitionCollectDto VideoExhibitionCollectDto = new VideoExhibitionCollectDto();
       
        BeanUtils.copyProperties(VideoExhibitionCollectDto,this);
       
        return VideoExhibitionCollectDto;
    }

}
