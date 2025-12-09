package com.example.web.dto;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

import org.apache.commons.beanutils.BeanUtils;

import com.example.web.entity.Activity;
import com.example.web.enums.ActivityStatusEnum;
import com.example.web.tools.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Data;

/**
 * 活动类
 */
@Data
public class ActivityDto extends BaseDto {

    /**
     * 活动类型
     */
    @JsonProperty("ActivityType")
    private String ActivityType;

    /**
     * 活动标题
     */
    @JsonProperty("Title")
    private String Title;
    /**
     * 起始活动时间
     */
    @JsonProperty("BeginActiveTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime BeginActiveTime;
    /**
     * 截至活动时间
     */
    @JsonProperty("EndActiveTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime EndActiveTime;

    /**
     * 地点
     */
    @JsonProperty("Location")
    private String Location;

    /**
     * 封面
     */
    @JsonProperty("Cover")
    private String Cover;

    /**
     * 起始报名时间
     */
    @JsonProperty("BeginJoinTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime BeginJoinTime;
    /**
     * 截至报名时间
     */
    @JsonProperty("EndJoinTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime EndJoinTime;

    /**
     * 活动状态
     */
    @JsonProperty("ActivityStatus")
    private Integer ActivityStatus;

    public String getActivityStatusFormat() {
        return ActivityStatusEnum.GetEnum(ActivityStatus).toString();
    }

    private String ActivityStatusFormat;

    /**
     * 活动内容
     */
    @JsonProperty("Content")
    private String Content;

    /**
     * 限制人数
     */
    @JsonProperty("LimitPeople")
    private Integer LimitPeople;

    /**
     * 发起人
     */
    @JsonProperty("UserId")
    private Integer UserId;

    @JsonProperty("UserDto")
    private AppUserDto UserDto;

    /**
     * 是否已经报名
     */
    @JsonProperty("IsJoin")
    private Boolean IsJoin;

    @JsonProperty("JoinCount")
    private Long JoinCount;

    /**
     * 把活动传输模型转换成活动实体
     */
    public Activity MapToEntity() throws InvocationTargetException, IllegalAccessException {
        Activity Activity = new Activity();

        BeanUtils.copyProperties(Activity, this);

        return Activity;
    }

}
