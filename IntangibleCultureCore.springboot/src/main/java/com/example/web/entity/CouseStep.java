package com.example.web.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.web.dto.CouseStepDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
/**
 * 课程章节表
 */
@Data
@TableName("`CouseStep`")
public class CouseStep extends BaseEntity {

      
  	  /**
     * 课程小节
     */  
    @JsonProperty("Name")
    @TableField(value="Name",updateStrategy = FieldStrategy.IGNORED)
    private String Name;
      
    /**
     * 小节时长
     */  
    @JsonProperty("Durtion")
    @TableField(value="Durtion",updateStrategy = FieldStrategy.IGNORED)
    private Double Durtion;      
      
  	  /**
     * 小节封面
     */  
    @JsonProperty("Cover")
    @TableField(value="Cover",updateStrategy = FieldStrategy.IGNORED)
    private String Cover;
      
  	  /**
     * 小节视频
     */  
    @JsonProperty("VideoUrls")
    @TableField(value="VideoUrls",updateStrategy = FieldStrategy.IGNORED)
    private String VideoUrls;
      
  	  /**
     * 附件
     */  
    @JsonProperty("FileUrls")
    @TableField(value="FileUrls",updateStrategy = FieldStrategy.IGNORED)
    private String FileUrls;
      
    /**
     * 所属课程
     */  
    @JsonProperty("CouseId")
    @TableField(value="CouseId",updateStrategy = FieldStrategy.IGNORED)
    private Integer CouseId;          
  
    /**
     * 把课程章节实体转换成课程章节传输模型
     */
    public CouseStepDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        CouseStepDto CouseStepDto = new CouseStepDto();
        BeanUtils.copyProperties(CouseStepDto,this);
        return CouseStepDto;
    }

}
