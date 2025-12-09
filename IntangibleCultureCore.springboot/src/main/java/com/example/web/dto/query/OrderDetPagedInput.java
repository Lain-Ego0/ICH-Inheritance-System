package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OrderDet查询模型
 */
@NoArgsConstructor
@Data
public class OrderDetPagedInput extends PagedInput {

    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 订单
     */
    @JsonProperty("OrderId")
    private Integer OrderId;
    /**
     * 文创品
     */
    @JsonProperty("GoodId")
    private Integer GoodId;

}
