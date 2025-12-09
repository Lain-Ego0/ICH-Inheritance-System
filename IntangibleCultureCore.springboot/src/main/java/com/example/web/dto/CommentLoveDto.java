package com.example.web.dto;

import com.example.web.tools.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 评论点赞记录类
 */
@Data
public class CommentLoveDto extends BaseDto {

    /**
     * 关联id
     */

    @JsonProperty("RelativeId")
    private Integer RelativeId;

    /**

     */

    @JsonProperty("Type")
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

    @JsonProperty("UserDto")
    private AppUserDto UserDto;

    @JsonProperty("CommentDto")
    private CommentDto CommentDto;

}
