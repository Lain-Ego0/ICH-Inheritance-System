package com.example.web.dto;

import com.example.web.entity.GoodType;
import com.example.web.tools.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 文创品分类类
 */
@Data
public class GoodTypeDto extends BaseDto {

    /**
     * 名称
     */
    @JsonProperty("Name")
    private String Name;

    /**
     * 排序
     */
    @JsonProperty("Sort")
    private Integer Sort;

    /**
     * 把文创品分类传输模型转换成文创品分类实体
     */
    public GoodType MapToEntity() throws InvocationTargetException, IllegalAccessException {
        GoodType GoodType = new GoodType();
        BeanUtils.copyProperties(GoodType, this);
        return GoodType;
    }

}
