package com.example.web.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.web.tools.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 评论类
 */
@Data
public class CommentDto extends BaseDto {

    /**
     * 关联id
     */

    @JsonProperty("RelativeId")
    @TableField("RelativeId")
    private Integer RelativeId;

    /**

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

    /**
     * 发送用户
     */
    @JsonProperty("SendUserDto")
    private AppUserDto SendUserDto;

    /**
     * 目标用户
     */
    @JsonProperty("TargetUserDto")
    private AppUserDto TargetUserDto;
    /**
     * 点赞数
     */
    @JsonProperty("LikeCount")
    private Integer LikeCount;

    /**
     * 子评论
     */
    @JsonProperty("Children")
    private List<CommentDto> Children;
    /**
     * 是否点赞
     */
    @JsonProperty("IsLove")
    private Boolean IsLove;




}
