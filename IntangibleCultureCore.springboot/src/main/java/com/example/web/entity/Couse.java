package com.example.web.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.web.dto.CouseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 课程表
 */
@Data
@TableName("`Couse`")
public class Couse extends BaseEntity {

    /**
     * 课程名称
     */
    @JsonProperty("Name")
    @TableField(value = "Name", updateStrategy = FieldStrategy.IGNORED)
    private String Name;

    /**
     * 课程内容
     */
    @JsonProperty("Content")
    @TableField(value = "Content", updateStrategy = FieldStrategy.IGNORED)
    private String Content;

    /**
     * 课程分类
     */
    @JsonProperty("CouseTypeId")
    @TableField(value = "CouseTypeId", updateStrategy = FieldStrategy.IGNORED)
    private Integer CouseTypeId;

    /**
     * 封面
     */
    @JsonProperty("Cover")
    @TableField(value = "Cover", updateStrategy = FieldStrategy.IGNORED)
    private String Cover;

    /**
     * 是否上架
     */
    @JsonProperty("IsPutaway")
    @TableField(value = "IsPutaway", updateStrategy = FieldStrategy.IGNORED)
    private Boolean IsPutaway;

    /**
     * 金额
     */
    @JsonProperty("Price")
    @TableField(value = "Price", updateStrategy = FieldStrategy.IGNORED)
    private Double Price;

    /**
     * 是否免费
     */
    @JsonProperty("IsFree")
    @TableField(value = "IsFree", updateStrategy = FieldStrategy.IGNORED)
    private Boolean IsFree;

    /**
     * 把课程实体转换成课程传输模型
     */
    public CouseDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        CouseDto CouseDto = new CouseDto();
        BeanUtils.copyProperties(CouseDto, this);
        return CouseDto;
    }

}
