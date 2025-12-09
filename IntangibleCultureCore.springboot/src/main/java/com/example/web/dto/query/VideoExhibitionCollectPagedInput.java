package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 视频展览收藏查询模型
 */
@NoArgsConstructor
@Data
public class VideoExhibitionCollectPagedInput extends PagedInput {

    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 收藏人
     */
    @JsonProperty("CoolectUserId")
    private Integer CoolectUserId;

    /**
     * 视频展览Id
     */
    @JsonProperty("VideoExhibitionId")
    private Integer VideoExhibitionId;
}
