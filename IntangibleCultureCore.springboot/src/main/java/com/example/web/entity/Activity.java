package com.example.web.entity;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

import org.apache.commons.beanutils.BeanUtils;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.web.dto.ActivityDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Data;

/**
 * 活动表
 */
@Data
@TableName("`Activity`")
public class Activity extends BaseEntity {

    /**
     * 活动类型
     */
    @JsonProperty("ActivityType")
    @TableField(value = "ActivityType", updateStrategy = FieldStrategy.IGNORED)
    private String ActivityType;

    /**
     * 活动标题
     */
    @JsonProperty("Title")
    @TableField(value = "Title", updateStrategy = FieldStrategy.IGNORED)
    private String Title;

    /**
     * 起始活动时间
     */
    @JsonProperty("BeginActiveTime")
    @TableField(value = "BeginActiveTime", updateStrategy = FieldStrategy.IGNORED)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime BeginActiveTime;
    /**
     * 截至活动时间
     */
    @JsonProperty("EndActiveTime")
    @TableField(value = "EndActiveTime", updateStrategy = FieldStrategy.IGNORED)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime EndActiveTime;

    /**
     * 地点
     */
    @JsonProperty("Location")
    @TableField(value = "Location", updateStrategy = FieldStrategy.IGNORED)
    private String Location;

    /**
     * 起始报名时间
     */
    @JsonProperty("BeginJoinTime")
    @TableField(value = "BeginJoinTime", updateStrategy = FieldStrategy.IGNORED)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime BeginJoinTime;
    /**
     * 截至报名时间
     */
    @JsonProperty("EndJoinTime")
    @TableField(value = "EndJoinTime", updateStrategy = FieldStrategy.IGNORED)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime EndJoinTime;

    /**
     * 活动状态
     */
    @JsonProperty("ActivityStatus")
    @TableField(value = "ActivityStatus", updateStrategy = FieldStrategy.IGNORED)
    private Integer ActivityStatus;

    /**
     * 活动内容
     */
    @JsonProperty("Content")
    @TableField(value = "Content", updateStrategy = FieldStrategy.IGNORED)
    private String Content;

    /**
     * 限制人数
     */
    @JsonProperty("LimitPeople")
    @TableField(value = "LimitPeople", updateStrategy = FieldStrategy.IGNORED)
    private Integer LimitPeople;

    /**
     * 发起人
     */
    @JsonProperty("UserId")
    @TableField(value = "UserId", updateStrategy = FieldStrategy.IGNORED)
    private Integer UserId;

    /**
     * 封面
     */
    @JsonProperty("Cover")
    @TableField(value = "Cover", updateStrategy = FieldStrategy.IGNORED)
    private String Cover;

    /**
     * 把活动实体转换成活动传输模型
     */
    public ActivityDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        ActivityDto ActivityDto = new ActivityDto();

        BeanUtils.copyProperties(ActivityDto, this);

        return ActivityDto;
    }

}
