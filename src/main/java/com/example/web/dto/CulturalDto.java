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
 * 非遗文化类
 */
@Data
public class CulturalDto extends BaseDto
{



    /**
     * 名称
     */
    @JsonProperty("Name")
    private String Name;


    /**
     * 编号
     */
    @JsonProperty("No")
    private String No;


    /**
     * 图片
     */
    @JsonProperty("ImageUrls")
    private String ImageUrls;


    /**
     * 登记日期
     */
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("RecordDate")
    private LocalDateTime RecordDate;


    /**
     * 申报地区
     */
    @JsonProperty("PlaceDeclaration")
    private String PlaceDeclaration;


    /**
     * 保护单位
     */
    @JsonProperty("ProtectionUnit")
    private String ProtectionUnit;


    /**
     * 类别
     */
    @JsonProperty("CulturalTypeId")
    private Integer CulturalTypeId;


    /**
     * 省市区
     */
    @JsonProperty("ProviceCityArea")
    private String ProviceCityArea;


    /**
     * 批次
     */
    @JsonProperty("BatchNo")
    private String BatchNo;


    /**
     * 传人
     */
    @JsonProperty("InheritorId")
    private Integer InheritorId;


    /**
     * 详细内容
     */
    @JsonProperty("Content")
    private String Content;

    @JsonProperty("InheritorDto")
    private InheritorDto InheritorDto;

    @JsonProperty("CulturalTypeDto")
    private CulturalTypeDto CulturalTypeDto;

    /**
     * 把非遗文化传输模型转换成非遗文化实体
     */
    public Cultural MapToEntity() throws InvocationTargetException, IllegalAccessException {
        Cultural Cultural= new Cultural();

        BeanUtils.copyProperties(Cultural,this);

        return Cultural;
    }

}
