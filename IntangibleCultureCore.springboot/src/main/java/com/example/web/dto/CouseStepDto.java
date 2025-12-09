package com.example.web.dto;

import com.example.web.entity.CouseStep;
import com.example.web.tools.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 课程章节类
 */
@Data
public class CouseStepDto extends BaseDto {

    /**
     * 课程小节
     */
    @JsonProperty("Name")
    private String Name;

    /**
     * 小节时长
     */
    @JsonProperty("Durtion")
    private Double Durtion;

    /**
     * 小节封面
     */
    @JsonProperty("Cover")
    private String Cover;

    /**
     * 小节视频
     */
    @JsonProperty("VideoUrls")
    private String VideoUrls;

    /**
     * 附件
     */
    @JsonProperty("FileUrls")
    private String FileUrls;

    /**
     * 所属课程
     */
    @JsonProperty("CouseId")
    private Integer CouseId;

    @JsonProperty("CouseDto")
    private CouseDto CouseDto;

    @JsonProperty("CouseClockCount")
    private Integer CouseClockCount;

    /**
     * 时长
     */
    @JsonProperty("StudyTotalDurtion")
    private Double StudyTotalDurtion;

    /**
     * 是否打卡
     */
    @JsonProperty("IsClock")
    private Boolean IsClock;

    /**
     * 把课程章节传输模型转换成课程章节实体
     */
    public CouseStep MapToEntity() throws InvocationTargetException, IllegalAccessException {
        CouseStep CouseStep = new CouseStep();
        BeanUtils.copyProperties(CouseStep, this);
        return CouseStep;
    }

}
