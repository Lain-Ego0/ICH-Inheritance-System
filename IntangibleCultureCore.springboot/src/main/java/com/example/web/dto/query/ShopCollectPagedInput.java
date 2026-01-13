package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ShopCollect查询模型
 */
@NoArgsConstructor
@Data
public class ShopCollectPagedInput extends PagedInput {
    
    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
     /**
     * 店铺
     */
  	 @JsonProperty("ShopId")
    private Integer ShopId;
     /**
     * 收藏人
     */
  	 @JsonProperty("CollectUserId")
    private Integer CollectUserId;

}
