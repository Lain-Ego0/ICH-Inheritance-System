package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * GoodStock查询模型
 */
@NoArgsConstructor
@Data
public class GoodStockPagedInput extends PagedInput {

    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 备注模糊查询条件
     */
    @JsonProperty("Remark")
    private String Remark;
    /**
     * 文创品
     */
    @JsonProperty("GoodId")
    private Integer GoodId;

}
