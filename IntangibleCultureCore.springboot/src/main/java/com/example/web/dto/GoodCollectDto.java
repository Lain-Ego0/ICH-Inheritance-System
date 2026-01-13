package com.example.web.dto;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.example.web.entity.GoodCollect;
import com.example.web.tools.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 文创品收藏类
 */
@Data
public class GoodCollectDto extends BaseDto {

    /**
     * 文创品
     */
    @JsonProperty("GoodId")
    private Integer GoodId;

    /**
     * 收藏人
     */
    @JsonProperty("CollectUserId")
    private Integer CollectUserId;

    @JsonProperty("CollectUserDto")
    private AppUserDto CollectUserDto;

    @JsonProperty("GoodDto")
    private GoodDto GoodDto;

    /**
     * 把文创品收藏传输模型转换成文创品收藏实体
     */
    public GoodCollect MapToEntity() throws InvocationTargetException, IllegalAccessException {
        GoodCollect GoodCollect = new GoodCollect();
        BeanUtils.copyProperties(GoodCollect, this);
        return GoodCollect;
    }

}
