package com.example.web.dto.query;

import java.time.LocalDateTime;
import java.util.List;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ActivityRecord查询模型
 */
@NoArgsConstructor
@Data
public class ActivityRecordPagedInput extends PagedInput {

    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 审核结果模糊查询条件
     */
    @JsonProperty("AuditResult")
    private String AuditResult;
    /**
     * 评价模糊查询条件
     */
    @JsonProperty("Comment")
    private String Comment;
    /**
     * 活动
     */
    @JsonProperty("ActivityId")
    private Integer ActivityId;
    /**
     * 报名人
     */
    @JsonProperty("JoinUserId")
    private Integer JoinUserId;
    /**
     * 审核状态
     */
    @JsonProperty("ActivityAuditStatus")
    private Integer ActivityAuditStatus;
    /**
     * 取消时间时间范围
     */
    @JsonProperty("CancelTimeRange")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> CancelTimeRange;
    /**
     * 签到时间时间范围
     */
    @JsonProperty("CheckTimeRange")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> CheckTimeRange;
    /**
     * 是否取消
     */
    @JsonProperty("IsCancel")
    private Boolean IsCancel;
    /**
     * 是否签到
     */
    @JsonProperty("IsCheck")
    private Boolean IsCheck;

    /**
     * 签到码
     */
    @JsonProperty("CheckCode")
    private String CheckCode;

}
