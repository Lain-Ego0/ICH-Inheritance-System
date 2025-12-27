package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * GoodProp查询模型
 */
@NoArgsConstructor
@Data
public class GoodPropPagedInput extends PagedInput {

    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 属性名称模糊查询条件
     */
    @JsonProperty("NameLike")
    private String NameLike;
    /**
     * 属性值模糊查询条件
     */
    @JsonProperty("ValueLike")
    private String ValueLike;
    /**
     * 文创品
     */
    @JsonProperty("GoodId")
    private Integer GoodId;

}
