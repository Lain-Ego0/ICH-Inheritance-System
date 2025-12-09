package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * CouseOrder查询模型
 */
@NoArgsConstructor
@Data
public class CouseOrderPagedInput extends PagedInput {

    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 订单编号模糊查询条件
     */
    @JsonProperty("No")
    private String No;
    /**
     * 支付类型模糊查询条件
     */
    @JsonProperty("PayType")
    private String PayType;
    /**
     * 课程
     */
    @JsonProperty("CouseId")
    private Integer CouseId;
    /**
     * 下单人
     */
    @JsonProperty("UserId")
    private Integer UserId;
    /**
     * 退款时间时间范围
     */
    @JsonProperty("ReturnTimeRange")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> ReturnTimeRange;
    /**
     * 是否退款
     */
    @JsonProperty("IsReturn")
    private Boolean IsReturn;

    /**
     * 对应老师
     */
    @JsonProperty("TeachUserId")
    private Integer TeachUserId;
    
}
