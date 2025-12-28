package com.example.web.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 评论表
 */
@Data
@TableName("Comment")
public class Comment extends BaseEntity {

    /**
     * 关联id
     */

    @JsonProperty("RelativeId")
    @TableField("RelativeId")
    private Integer RelativeId;

    /**
     *
     */

    @JsonProperty("Type")
    @TableField("Type")
    private String Type;
    /**
     * 评论内容
     */
    @JsonProperty("Content")
    private String Content;
    /**
     * 名称
     */
    @JsonProperty("NickName")
    private String NickName;
    /**
     * 头像
     */
    @JsonProperty("HeadImage")
    private String HeadImage;
    /**
     * 根评论
     */
    @JsonProperty("RootCommentId")
    private Integer RootCommentId;

    /**
     * 回复用户
     */
    @JsonProperty("TargetUserId")
    private Integer TargetUserId;

    /**
     * 发送用户
     */
    @JsonProperty("SendUserId")
    private Integer SendUserId;
}
