package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TopicRecord查询模型
 */
@NoArgsConstructor
@Data
public class TopicRecordPagedInput extends PagedInput {
    
    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
     /**
     * 话题类型
     */
  	 @JsonProperty("TopicTypeId")
    private Integer TopicTypeId;
     /**
     * 话题
     */
  	 @JsonProperty("TopicId")
    private Integer TopicId;
     /**
     * 浏览人
     */
  	 @JsonProperty("UserId")
    private Integer UserId;

}
