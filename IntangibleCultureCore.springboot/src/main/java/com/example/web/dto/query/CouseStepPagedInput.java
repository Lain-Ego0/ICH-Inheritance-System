package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CouseStep查询模型
 */
@NoArgsConstructor
@Data
public class CouseStepPagedInput extends PagedInput {

    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 课程小节模糊查询条件
     */
    @JsonProperty("Name")
    private String Name;
    /**
     * 所属课程
     */
    @JsonProperty("CouseId")
    private Integer CouseId;

    /**
     * 对应老师
     */
    @JsonProperty("TeachUserId")
    private Integer TeachUserId;

    /**
     * 课程步骤
     */
    @JsonProperty("CouseStepId")
    private Integer CouseStepId;
}
