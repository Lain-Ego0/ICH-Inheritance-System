package com.example.web.entity;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.web.dto.GoodStockDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 库存记录表
 */
@Data
@TableName("`GoodStock`")
public class GoodStock extends BaseEntity {

    /**
     * 文创品
     */
    @JsonProperty("GoodId")
    @TableField(value = "GoodId", updateStrategy = FieldStrategy.IGNORED)
    private Integer GoodId;

    /**
     * 调整数量
     */
    @JsonProperty("Qty")
    @TableField(value = "Qty", updateStrategy = FieldStrategy.IGNORED)
    private Double Qty;

    /**
     * 备注
     */
    @JsonProperty("Remark")
    @TableField(value = "Remark", updateStrategy = FieldStrategy.IGNORED)
    private String Remark;

    /**
     * 把库存记录实体转换成库存记录传输模型
     */
    public GoodStockDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        GoodStockDto GoodStockDto = new GoodStockDto();
        BeanUtils.copyProperties(GoodStockDto, this);
        return GoodStockDto;
    }

}
