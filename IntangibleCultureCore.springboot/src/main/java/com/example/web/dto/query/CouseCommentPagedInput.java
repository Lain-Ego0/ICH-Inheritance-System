package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课程评价查询模型
 */
@NoArgsConstructor
@Data
public class CouseCommentPagedInput extends PagedInput {

    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 评语模糊查询条件
     */
    @JsonProperty("Comment")
    private String Comment;
    /**
     * 课程
     */
    @JsonProperty("CouseId")
    private Integer CouseId;
    /**
     * 评语人
     */
    @JsonProperty("UserId")
    private Integer UserId;

    /**
     * 对应老师
     */
    @JsonProperty("TeachUserId")
    private Integer TeachUserId;

}
