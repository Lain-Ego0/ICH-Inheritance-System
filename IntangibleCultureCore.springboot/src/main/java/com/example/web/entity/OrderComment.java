package com.example.web.entity;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.web.dto.OrderCommentDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 订单评价表
 */
@Data
@TableName("`OrderComment`")
public class OrderComment extends BaseEntity {

    /**
     * 文创品
     */
    @JsonProperty("GoodId")
    @TableField(value = "GoodId", updateStrategy = FieldStrategy.IGNORED)
    private Integer GoodId;

    /**
     * 订单
     */
    @JsonProperty("OrderId")
    @TableField(value = "OrderId", updateStrategy = FieldStrategy.IGNORED)
    private Integer OrderId;

    /**
     * 评分
     */
    @JsonProperty("CommentScore")
    @TableField(value = "CommentScore", updateStrategy = FieldStrategy.IGNORED)
    private Double CommentScore;

    /**
     * 评论
     */
    @JsonProperty("Comment")
    @TableField(value = "Comment", updateStrategy = FieldStrategy.IGNORED)
    private String Comment;

    /**
     * 店铺
     */
    @JsonProperty("ShopId")
    @TableField(value = "ShopId", updateStrategy = FieldStrategy.IGNORED)
    private Integer ShopId;

    /**
     * 图片
     */
    @JsonProperty("ImageUrls")
    @TableField(value = "ImageUrls", updateStrategy = FieldStrategy.IGNORED)
    private String ImageUrls;

    /**
     * 评论人
     */
    @JsonProperty("CommentUserId")
    @TableField(value = "CommentUserId", updateStrategy = FieldStrategy.IGNORED)
    private Integer CommentUserId;

    /**
     * 把订单评价实体转换成订单评价传输模型
     */
    public OrderCommentDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        OrderCommentDto OrderCommentDto = new OrderCommentDto();
        BeanUtils.copyProperties(OrderCommentDto, this);
        return OrderCommentDto;
    }

}
