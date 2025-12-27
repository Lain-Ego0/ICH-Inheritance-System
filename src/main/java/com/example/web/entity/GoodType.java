package com.example.web.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.web.dto.GoodTypeDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 文创品分类表
 */
@Data
@TableName("`GoodType`")
public class GoodType extends BaseEntity {

    /**
     * 名称
     */
    @JsonProperty("Name")
    @TableField(value = "Name", updateStrategy = FieldStrategy.IGNORED)
    private String Name;

    /**
     * 排序
     */
    @JsonProperty("Sort")
    @TableField(value = "Sort", updateStrategy = FieldStrategy.IGNORED)
    private Integer Sort;

    /**
     * 把文创品分类实体转换成文创品分类传输模型
     */
    public GoodTypeDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        GoodTypeDto GoodTypeDto = new GoodTypeDto();
        BeanUtils.copyProperties(GoodTypeDto, this);
        return GoodTypeDto;
    }

}
