package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BuyCard查询模型
 */
@NoArgsConstructor
@Data
public class BuyCardPagedInput extends PagedInput {

    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 店铺
     */
    @JsonProperty("ShopId")
    private Integer ShopId;
    /**
     * 所属用户
     */
    @JsonProperty("BelongUserId")
    private Integer BelongUserId;
    /**
     * 文创品
     */
    @JsonProperty("GoodId")
    private Integer GoodId;

}
