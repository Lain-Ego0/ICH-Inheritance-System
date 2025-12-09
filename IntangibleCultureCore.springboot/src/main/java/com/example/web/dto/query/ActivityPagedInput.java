package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.util.List;

/**
 * Activity查询模型
 */
@NoArgsConstructor
@Data
public class ActivityPagedInput extends PagedInput {

    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 活动标题模糊查询条件
     */
    @JsonProperty("Title")
    private String Title;
    /**
     * 活动类型模糊查询条件
     */
    @JsonProperty("ActivityType")
    private String ActivityType;
    /**
     * 地点模糊查询条件
     */
    @JsonProperty("Location")
    private String Location;
    /**
     * 发起人
     */
    @JsonProperty("UserId")
    private Integer UserId;
    /**
     * 活动状态
     */
    @JsonProperty("ActivityStatus")
    private Integer ActivityStatus;
    /**
     * 活动时间时间范围
     */
    @JsonProperty("ActiveTimeRange")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> ActiveTimeRange;
    /**
     * 活动内容模糊查询条件
     */
    @JsonProperty("Content")
    private String Content;
    /**
     * 报名时间时间范围
     */
    @JsonProperty("JoinTimeRange")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> JoinTimeRange;

}
