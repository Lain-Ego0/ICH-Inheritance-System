package com.example.web.entity;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

import org.apache.commons.beanutils.BeanUtils;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.web.dto.ActivityRecordDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Data;

/**
 * 活动登记表
 */
@Data
@TableName("`ActivityRecord`")
public class ActivityRecord extends BaseEntity {

    /**
     * 审核结果
     */
    @JsonProperty("AuditResult")
    @TableField(value = "AuditResult", updateStrategy = FieldStrategy.IGNORED)
    private String AuditResult;

    /**
     * 是否签到
     */
    @JsonProperty("IsCheck")
    @TableField(value = "IsCheck", updateStrategy = FieldStrategy.IGNORED)
    private Boolean IsCheck;

    /**
     * 是否取消
     */
    @JsonProperty("IsCancel")
    @TableField(value = "IsCancel", updateStrategy = FieldStrategy.IGNORED)
    private Boolean IsCancel;

    /**
     * 报名人
     */
    @JsonProperty("JoinUserId")
    @TableField(value = "JoinUserId", updateStrategy = FieldStrategy.IGNORED)
    private Integer JoinUserId;

    /**
     * 活动
     */
    @JsonProperty("ActivityId")
    @TableField(value = "ActivityId", updateStrategy = FieldStrategy.IGNORED)
    private Integer ActivityId;

    /**
     * 评分
     */
    @JsonProperty("CommentScore")
    @TableField(value = "CommentScore", updateStrategy = FieldStrategy.IGNORED)
    private Double CommentScore;

    /**
     * 评价
     */
    @JsonProperty("Comment")
    @TableField(value = "Comment", updateStrategy = FieldStrategy.IGNORED)
    private String Comment;

    /**
     * 审核状态
     */
    @JsonProperty("ActivityAuditStatus")
    @TableField(value = "ActivityAuditStatus", updateStrategy = FieldStrategy.IGNORED)
    private Integer ActivityAuditStatus;

    /**
     * 签到时间
     */
    @JsonProperty("CheckTime")
    @TableField(value = "CheckTime", updateStrategy = FieldStrategy.IGNORED)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime CheckTime;

    /**
     * 取消时间
     */
    @JsonProperty("CancelTime")
    @TableField(value = "CancelTime", updateStrategy = FieldStrategy.IGNORED)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime CancelTime;

    /**
     * 签到码
     */
    @JsonProperty("CheckCode")
    @TableField(value = "CheckCode", updateStrategy = FieldStrategy.IGNORED)
    private String CheckCode;

    /**
     * 把活动登记实体转换成活动登记传输模型
     */
    public ActivityRecordDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        ActivityRecordDto ActivityRecordDto = new ActivityRecordDto();

        BeanUtils.copyProperties(ActivityRecordDto, this);

        return ActivityRecordDto;
    }

}
