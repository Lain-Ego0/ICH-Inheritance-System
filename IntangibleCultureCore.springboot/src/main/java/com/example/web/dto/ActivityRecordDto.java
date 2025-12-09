package com.example.web.dto;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

import org.apache.commons.beanutils.BeanUtils;

import com.example.web.entity.ActivityRecord;
import com.example.web.enums.ActivityAuditStatusEnum;
import com.example.web.tools.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Data;

/**
 * 活动登记类
 */
@Data
public class ActivityRecordDto extends BaseDto {

    /**
     * 审核结果
     */
    @JsonProperty("AuditResult")
    private String AuditResult;

    /**
     * 是否签到
     */
    @JsonProperty("IsCheck")
    private Boolean IsCheck;

    /**
     * 是否取消
     */
    @JsonProperty("IsCancel")
    private Boolean IsCancel;

    /**
     * 报名人
     */
    @JsonProperty("JoinUserId")
    private Integer JoinUserId;

    /**
     * 活动
     */
    @JsonProperty("ActivityId")
    private Integer ActivityId;

    /**
     * 评分
     */
    @JsonProperty("CommentScore")
    private Double CommentScore;

    /**
     * 评价
     */
    @JsonProperty("Comment")
    private String Comment;

    /**
     * 签到码
     */
    @JsonProperty("CheckCode")
    private String CheckCode;

    /**
     * 输入的签到码
     */
    @JsonProperty("InputCheckCode")
    private String InputCheckCode;

    /**
     * 审核状态
     */
    @JsonProperty("ActivityAuditStatus")
    private Integer ActivityAuditStatus;

    public String getActivityAuditStatusFormat() {
        return ActivityAuditStatusEnum.GetEnum(ActivityAuditStatus).toString();
    }

    private String ActivityAuditStatusFormat;

    /**
     * 签到时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("CheckTime")
    private LocalDateTime CheckTime;

    /**
     * 取消时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("CancelTime")
    private LocalDateTime CancelTime;

    @JsonProperty("JoinUserDto")
    private AppUserDto JoinUserDto;

    @JsonProperty("ActivityDto")
    private ActivityDto ActivityDto;

    /**
     * 把活动登记传输模型转换成活动登记实体
     */
    public ActivityRecord MapToEntity() throws InvocationTargetException, IllegalAccessException {
        ActivityRecord ActivityRecord = new ActivityRecord();

        BeanUtils.copyProperties(ActivityRecord, this);

        return ActivityRecord;
    }

}
