package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Good查询模型
 */
@NoArgsConstructor
@Data
public class GoodPagedInput extends PagedInput {

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
     * 单位模糊查询条件
     */
    @JsonProperty("UnitLike")
    private String UnitLike;
    /**
     * 所属店铺
     */
    @JsonProperty("ShopId")
    private Integer ShopId;

    /**
     * 审核状态
     */
    @JsonProperty("AuditStatus")
    private Integer AuditStatus;
    /**
     * 分类
     */
    @JsonProperty("GoodTypeId")
    private Integer GoodTypeId;

    /**
     * 店铺分类
     */
    @JsonProperty("ShopGoodTypeId")
    private Integer ShopGoodTypeId;

    /**
     * 是否店铺分类
     */
    @JsonProperty("IsShopGoodType")
    private Boolean IsShopGoodType;

    /**
     * 详情内容模糊查询条件
     */
    @JsonProperty("ContentLike")
    private String ContentLike;

    /**
     * 关键词搜索
     */
    @JsonProperty("KeyWord")
    private String KeyWord;

    @JsonProperty("GoodIds")
    private List<Integer> GoodIds;

    /**
     * 是否需要补货
     */
    @JsonProperty("IsReplenishment")
    private Boolean IsReplenishment;

}
