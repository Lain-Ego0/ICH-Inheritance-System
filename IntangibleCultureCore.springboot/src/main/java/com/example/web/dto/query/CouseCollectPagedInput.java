package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课程收藏查询模型
 */
@NoArgsConstructor
@Data
public class CouseCollectPagedInput extends PagedInput {

    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 课程
     */
    @JsonProperty("CouseId")
    private Integer CouseId;
    /**
     * 收藏人
     */
    @JsonProperty("CollectUserId")
    private Integer CollectUserId;

    /**
     * 对应老师
     */
    @JsonProperty("TeachUserId")
    private Integer TeachUserId;
}
