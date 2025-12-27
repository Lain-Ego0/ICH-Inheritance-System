package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * GoodType查询模型
 */
@NoArgsConstructor
@Data
public class GoodTypePagedInput extends PagedInput {

    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 名称模糊查询条件
     */
    @JsonProperty("NameLike")
    private String NameLike;

    /**
     * 是否店铺
     */
    @JsonProperty("IsShop")
    private Boolean IsShop;

    /**
     * 店铺编号
     */
    @JsonProperty("ShopId")
    private Integer ShopId;

}
