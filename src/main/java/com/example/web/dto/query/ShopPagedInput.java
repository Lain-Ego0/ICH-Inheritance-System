package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Shop查询模型
 */
@NoArgsConstructor
@Data
public class ShopPagedInput extends PagedInput {
    
    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 店铺名称模糊查询条件
     */
  	 @JsonProperty("NameLike")
    private String NameLike;
    /**
     * 店铺地址模糊查询条件
     */
  	 @JsonProperty("AddressLike")
    private String AddressLike;

    /**
     * 审核原因模糊查询条件
     */
  	 @JsonProperty("AuditReasonLike")
    private String AuditReasonLike;
    /**
     * 店铺保障模糊查询条件
     */
  	 @JsonProperty("EnsureLike")
    private String EnsureLike;
     /**
     * 商家
     */
  	 @JsonProperty("UserId")
    private Integer UserId;
     /**
     * 审核状态
     */
  	 @JsonProperty("AuditStatus")
    private Integer AuditStatus;
     /**
     * 审核人
     */
  	 @JsonProperty("AuditUserId")
    private Integer AuditUserId;
    /**
     * 店铺详情模糊查询条件
     */
  	 @JsonProperty("ContentLike")
    private String ContentLike;

}
