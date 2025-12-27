package com.example.web.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.web.dto.GoodDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 文创品表
 */
@Data
@TableName("`Good`")
public class Good extends BaseEntity {

    /**
     * 名称
     */
    @JsonProperty("Name")
    @TableField(value = "Name", updateStrategy = FieldStrategy.IGNORED)
    private String Name;

    /**
     * 封面
     */
    @JsonProperty("Cover")
    @TableField(value = "Cover", updateStrategy = FieldStrategy.IGNORED)
    private String Cover;

    /**
     * 主图
     */
    @JsonProperty("ImageUrls")
    @TableField(value = "ImageUrls", updateStrategy = FieldStrategy.IGNORED)
    private String ImageUrls;

    /**
     * 价格
     */
    @JsonProperty("Price")
    @TableField(value = "Price", updateStrategy = FieldStrategy.IGNORED)
    private Double Price;

    /**
     * 库存
     */
    @JsonProperty("Stock")
    @TableField(value = "Stock", updateStrategy = FieldStrategy.IGNORED)
    private Double Stock;

    /**
     * 详情内容
     */
    @JsonProperty("Content")
    @TableField(value = "Content", updateStrategy = FieldStrategy.IGNORED)
    private String Content;

    /**
     * 所属店铺
     */
    @JsonProperty("ShopId")
    @TableField(value = "ShopId", updateStrategy = FieldStrategy.IGNORED)
    private Integer ShopId;

    /**
     * 单位
     */
    @JsonProperty("Unit")
    @TableField(value = "Unit", updateStrategy = FieldStrategy.IGNORED)
    private String Unit;

    /**
     * 分类
     */
    @JsonProperty("GoodTypeId")
    @TableField(value = "GoodTypeId", updateStrategy = FieldStrategy.IGNORED)
    private Integer GoodTypeId;

    /**
     * 库存下线
     */
    @JsonProperty("ReplenishmentLimit")
    @TableField(value = "ReplenishmentLimit", updateStrategy = FieldStrategy.IGNORED)
    private Integer ReplenishmentLimit;

    /**
     * 把文创品实体转换成文创品传输模型
     */
    public GoodDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        GoodDto GoodDto = new GoodDto();
        BeanUtils.copyProperties(GoodDto, this);
        return GoodDto;
    }

}
