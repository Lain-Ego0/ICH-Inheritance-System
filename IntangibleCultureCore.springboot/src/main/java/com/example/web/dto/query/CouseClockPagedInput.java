package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * CouseClock查询模型
 */
@NoArgsConstructor
@Data
public class CouseClockPagedInput extends PagedInput {

    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 所属课程
     */
    @JsonProperty("CouseId")
    private Integer CouseId;
    /**
     * 打卡章节
     */
    @JsonProperty("CouseStepId")
    private Integer CouseStepId;
    /**
     * 用户
     */
    @JsonProperty("UserId")
    private Integer UserId;
    /**
     * 打卡时间时间范围
     */
    @JsonProperty("ClockTimeRange")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> ClockTimeRange;

    /**
     * 对应老师
     */
    @JsonProperty("TeachUserId")
    private Integer TeachUserId;

}
