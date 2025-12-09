package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * CouseStudySchedule查询模型
 */
@NoArgsConstructor
@Data
public class CouseStudySchedulePagedInput extends PagedInput {

    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 学习人
     */
    @JsonProperty("StudyUserId")
    private Integer StudyUserId;
    /**
     * 所属章节
     */
    @JsonProperty("CouseStepId")
    private Integer CouseStepId;
    /**
     * 所属课程
     */
    @JsonProperty("CouseId")
    private Integer CouseId;
    /**
     * 开始时间时间范围
     */
    @JsonProperty("BeginTimeRange")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> BeginTimeRange;
    /**
     * 最后上传时间时间范围
     */
    @JsonProperty("LastUploadTimeRange")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> LastUploadTimeRange;
    /**
     * 对应老师
     */
    @JsonProperty("TeachUserId")
    private Integer TeachUserId;
}
