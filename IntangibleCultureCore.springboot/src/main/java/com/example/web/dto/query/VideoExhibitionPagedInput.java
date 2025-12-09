package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 视频展览查询模型
 */
@NoArgsConstructor
@Data
public class VideoExhibitionPagedInput extends PagedInput {

    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 短描述模糊查询条件
     */
    @JsonProperty("ShortDesc")
    private String ShortDesc;
    /**
     * 省市区模糊查询条件
     */
    @JsonProperty("ProviceCityArea")
    private String ProviceCityArea;
    /**
     * 是否上架
     */
    @JsonProperty("IsPutaway")
    private Boolean IsPutaway;

    /**
     * 非遗类型VideoExhibitionType
     */
    @JsonProperty("VideoExhibitionTypeId")
    private Integer VideoExhibitionTypeId;
}
