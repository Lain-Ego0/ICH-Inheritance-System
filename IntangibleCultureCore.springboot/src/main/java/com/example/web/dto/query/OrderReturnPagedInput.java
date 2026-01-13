package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OrderReturn查询模型
 */
@NoArgsConstructor
@Data
public class OrderReturnPagedInput extends PagedInput {
    
    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
     /**
     * 订单
     */
  	 @JsonProperty("OrderId")
    private Integer OrderId;
     /**
     * 用户
     */
  	 @JsonProperty("UserId")
    private Integer UserId;
     /**
     * 商家
     */
  	 @JsonProperty("ShopUserId")
    private Integer ShopUserId;

    /**
     * 本次申请是否失效
     */
    @JsonProperty("IsDiscard")
    private Boolean IsDiscard;
}
