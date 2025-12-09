package com.example.web.dto.query;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Comment查询模型
 */
@NoArgsConstructor
@Data
public class CommentPagedInput extends PagedInput {

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
     *
     */

    @JsonProperty("Type")
    @TableField("Type")
    private String Type;
    /**
     * 名称
     */
    @JsonProperty("NickName")
    private String NickName;

}
