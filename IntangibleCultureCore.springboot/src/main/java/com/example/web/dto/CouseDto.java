package com.example.web.dto;

import com.example.web.entity.Couse;
import com.example.web.tools.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 课程类
 */
@Data
public class CouseDto extends BaseDto {

    /**
     * 课程名称
     */
    @JsonProperty("Name")
    private String Name;

    /**
     * 课程内容
     */
    @JsonProperty("Content")
    private String Content;

    /**
     * 课程分类
     */
    @JsonProperty("CouseTypeId")
    private Integer CouseTypeId;

    /**
     * 封面
     */
    @JsonProperty("Cover")
    private String Cover;

    /**
     * 是否上架
     */
    @JsonProperty("IsPutaway")
    private Boolean IsPutaway;

    @JsonProperty("CouseTypeDto")
    private CouseTypeDto CouseTypeDto;

    @JsonProperty("CouseStepDtos")
    private List<CouseStepDto> CouseStepDtos;

    // 打卡总人次
    @JsonProperty("CouseClockCount")
    private Integer TotalCouseClockCount;

    // 课程章节数量
    @JsonProperty("CouseStepCount")
    private Integer CouseStepCount;

    // 课程价格
    @JsonProperty("Price")
    private Double Price;

    // 是否免费
    @JsonProperty("IsFree")
    private Boolean IsFree;

    @JsonProperty("IsEnroll")
    private Boolean IsEnroll;

    @JsonProperty("EnrollUserCount")
    private Integer EnrollUserCount;

    /**
     * 把课程传输模型转换成课程实体
     */
    public Couse MapToEntity() throws InvocationTargetException, IllegalAccessException {
        Couse Couse = new Couse();
        BeanUtils.copyProperties(Couse, this);
        return Couse;
    }

}
