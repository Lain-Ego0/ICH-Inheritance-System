package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OrderComment查询模型
 */
@NoArgsConstructor
@Data
public class OrderCommentPagedInput extends PagedInput {

    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 评论模糊查询条件
     */
    @JsonProperty("CommentLike")
    private String CommentLike;
    /**
     * 文创品
     */
    @JsonProperty("GoodId")
    private Integer GoodId;
    /**
     * 订单
     */
    @JsonProperty("OrderId")
    private Integer OrderId;
    /**
     * 店铺
     */
    @JsonProperty("ShopId")
    private Integer ShopId;
    /**
     * 评论人
     */
    @JsonProperty("CommentUserId")
    private Integer CommentUserId;

}
