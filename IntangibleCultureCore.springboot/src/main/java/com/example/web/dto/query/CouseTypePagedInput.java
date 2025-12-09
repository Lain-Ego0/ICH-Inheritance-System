package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CouseType查询模型
 */
@NoArgsConstructor
@Data
public class CouseTypePagedInput extends PagedInput {
    
    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 分类模糊查询条件
     */
  	 @JsonProperty("Name")
    private String Name;

}
