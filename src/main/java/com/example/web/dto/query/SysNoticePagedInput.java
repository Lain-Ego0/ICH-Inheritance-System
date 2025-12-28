package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SysNotice查询模型
 */
@NoArgsConstructor
@Data
public class SysNoticePagedInput extends PagedInput {

    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 标题模糊查询条件
     */
    @JsonProperty("Title")
    private String Title;
    /**
     * 是否显示
     */
    @JsonProperty("IsPutaway")
    private Boolean IsPutaway;
    /**
     * 通知内容模糊查询条件
     */
    @JsonProperty("Content")
    private String Content;

}
