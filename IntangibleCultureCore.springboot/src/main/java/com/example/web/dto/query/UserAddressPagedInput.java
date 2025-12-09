package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserAddress查询模型
 */
@NoArgsConstructor
@Data
public class UserAddressPagedInput extends PagedInput {
    
    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 收货姓名模糊查询条件
     */
  	 @JsonProperty("NameLike")
    private String NameLike;
    /**
     * 电话模糊查询条件
     */
  	 @JsonProperty("PhoneLike")
    private String PhoneLike;

    /**
     * 省市区查询
     */
    @JsonProperty("ProviceCityAreaLike")
    private String ProviceCityAreaLike;

    /**
     * 详细地址
     */
    @JsonProperty("AddressLike")
    private String AddressLike;

    /**
     * 性别模糊查询条件
     */
  	 @JsonProperty("SexLike")
    private String SexLike;
    /**
     * 门牌号模糊查询条件
     */
  	 @JsonProperty("HouseNumberLike")
    private String HouseNumberLike;
     /**
     * 所属用户
     */
  	 @JsonProperty("BelongUserId")
    private Integer BelongUserId;

}
