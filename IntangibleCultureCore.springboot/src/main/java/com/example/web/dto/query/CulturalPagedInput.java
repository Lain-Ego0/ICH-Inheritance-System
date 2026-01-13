package com.example.web.dto.query;

import java.time.LocalDateTime;
import java.util.List;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 非遗文化查询模型
 */
@NoArgsConstructor
@Data
public class CulturalPagedInput extends PagedInput {

    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 名称模糊查询条件
     */
    @JsonProperty("Name")
    private String Name;
    /**
     * 编号模糊查询条件
     */
    @JsonProperty("No")
    private String No;
    /**
     * 申报地区模糊查询条件
     */
    @JsonProperty("PlaceDeclaration")
    private String PlaceDeclaration;
    /**
     * 保护单位模糊查询条件
     */
    @JsonProperty("ProtectionUnit")
    private String ProtectionUnit;
    /**
     * 省市区模糊查询条件
     */
    @JsonProperty("ProviceCityArea")
    private String ProviceCityArea;
    /**
     * 批次模糊查询条件
     */
    @JsonProperty("BatchNo")
    private String BatchNo;
    /**
     * 类别
     */
    @JsonProperty("CulturalTypeId")
    private Integer CulturalTypeId;
    /**
     * 传人
     */
    @JsonProperty("InheritorId")
    private Integer InheritorId;
    /**
     * 登记日期日期范围
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("RecordDateRange")
    private List<LocalDateTime> RecordDateRange;
    /**
     * 详细内容模糊查询条件
     */
    @JsonProperty("Content")
    private String Content;

    @JsonProperty("KeyWord")
    private String KeyWord;

}
