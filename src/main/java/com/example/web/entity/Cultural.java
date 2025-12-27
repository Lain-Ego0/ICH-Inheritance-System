package com.example.web.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.web.dto.CulturalDto;
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
 * 非遗文化表
 */
@Data
@TableName("`Cultural`")
public class Cultural extends BaseEntity {

      
  	  /**
     * 名称
     */  
    @JsonProperty("Name")
    @TableField(value="Name",updateStrategy = FieldStrategy.IGNORED)
    private String Name;
      
  	  /**
     * 编号
     */  
    @JsonProperty("No")
    @TableField(value="No",updateStrategy = FieldStrategy.IGNORED)
    private String No;
      
  	  /**
     * 图片
     */  
    @JsonProperty("ImageUrls")
    @TableField(value="ImageUrls",updateStrategy = FieldStrategy.IGNORED)
    private String ImageUrls;
      
    /**
     * 登记日期
     */  
    @JsonProperty("RecordDate")
    @TableField(value="RecordDate",updateStrategy = FieldStrategy.IGNORED)
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime RecordDate;             
      
  	  /**
     * 申报地区
     */  
    @JsonProperty("PlaceDeclaration")
    @TableField(value="PlaceDeclaration",updateStrategy = FieldStrategy.IGNORED)
    private String PlaceDeclaration;
      
  	  /**
     * 保护单位
     */  
    @JsonProperty("ProtectionUnit")
    @TableField(value="ProtectionUnit",updateStrategy = FieldStrategy.IGNORED)
    private String ProtectionUnit;
      
    /**
     * 类别
     */  
    @JsonProperty("CulturalTypeId")
    @TableField(value="CulturalTypeId",updateStrategy = FieldStrategy.IGNORED)
    private Integer CulturalTypeId;          
      
  	  /**
     * 省市区
     */  
    @JsonProperty("ProviceCityArea")
    @TableField(value="ProviceCityArea",updateStrategy = FieldStrategy.IGNORED)
    private String ProviceCityArea;
      
  	  /**
     * 批次
     */  
    @JsonProperty("BatchNo")
    @TableField(value="BatchNo",updateStrategy = FieldStrategy.IGNORED)
    private String BatchNo;
      
    /**
     * 传人
     */  
    @JsonProperty("InheritorId")
    @TableField(value="InheritorId",updateStrategy = FieldStrategy.IGNORED)
    private Integer InheritorId;          
      
    /**
     * 详细内容
     */  
    @JsonProperty("Content")
     @TableField(value="Content",updateStrategy = FieldStrategy.IGNORED)
    private String Content;
  
    /**
     * 把非遗文化实体转换成非遗文化传输模型
     */
    public CulturalDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        CulturalDto CulturalDto = new CulturalDto();
       
        BeanUtils.copyProperties(CulturalDto,this);
       
        return CulturalDto;
    }

}
