package com.example.web.dto.query;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CommentLove查询模型
 */
@NoArgsConstructor
@Data
public class CommentLovePagedInput extends PagedInput {

    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
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
     * 点赞评论
     */
    @JsonProperty("CommentId")
    private Integer CommentId;
    /**
     * 点赞人
     */
    @JsonProperty("UserId")
    private Integer UserId;

}
