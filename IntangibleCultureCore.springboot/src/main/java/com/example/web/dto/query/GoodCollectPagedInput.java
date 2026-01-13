package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * GoodCollect查询模型
 */
@NoArgsConstructor
@Data
public class GoodCollectPagedInput extends PagedInput {

    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 文创品
     */
    @JsonProperty("GoodId")
    private Integer GoodId;
    /**
     * 收藏人
     */
    @JsonProperty("CollectUserId")
    private Integer CollectUserId;

}
