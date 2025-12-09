package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Couse查询模型
 */
@NoArgsConstructor
@Data
public class CousePagedInput extends PagedInput {

    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 课程名称模糊查询条件
     */
    @JsonProperty("Name")
    private String Name;
    /**
     * 课程分类
     */
    @JsonProperty("CouseTypeId")
    private Integer CouseTypeId;
    /**
     * 课程内容模糊查询条件
     */
    @JsonProperty("Content")
    private String Content;
    /**
     * 是否上架
     */
    @JsonProperty("IsPutaway")
    private Boolean IsPutaway;

    /**
     * 是否免费
     */
    @JsonProperty("IsFree")
    private Boolean IsFree;

    /**
     * 关键字
     */
    @JsonProperty("KeyWord")
    private String KeyWord;

    
}
