package com.example.web.dto.query;

import java.time.LocalDateTime;
import java.util.List;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 传人查询模型
 */
@NoArgsConstructor
@Data
public class InheritorPagedInput extends PagedInput {

    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 姓名模糊查询条件
     */
    @JsonProperty("Name")
    private String Name;
    /**
     * 性别模糊查询条件
     */
    @JsonProperty("Sex")
    private String Sex;
    /**
     * 民族模糊查询条件
     */
    @JsonProperty("Nation")
    private String Nation;
    /**
     * 编号模糊查询条件
     */
    @JsonProperty("No")
    private String No;
    /**
     * 省市区模糊查询条件
     */
    @JsonProperty("ProviceCityArea")
    private String ProviceCityArea;
    /**
     * 出生年月日期范围
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("BirthDateRange")
    private List<LocalDateTime> BirthDateRange;
    /**
     * 内容模糊查询条件
     */
    @JsonProperty("Content")
    private String Content;

    /**
     * 关键字模糊查询条件
     */
    @JsonProperty("KeyWord")
    private String KeyWord;

}
