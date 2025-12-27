package com.example.web.dto;

import com.example.web.entity.GoodStock;
import com.example.web.tools.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 库存记录类
 */
@Data
public class GoodStockDto extends BaseDto {

    /**
     * 文创品
     */
    @JsonProperty("GoodId")
    private Integer GoodId;

    /**
     * 调整数量
     */
    @JsonProperty("Qty")
    private Double Qty;

    /**
     * 店铺
     */
    @JsonProperty("ShopId")
    private Integer ShopId;

    /**
     * 备注
     */
    @JsonProperty("Remark")
    private String Remark;

    @JsonProperty("GoodDto")
    private GoodDto GoodDto;

    @JsonProperty("ShopDto")
    private ShopDto ShopDto;

    /**
     * 把库存记录传输模型转换成库存记录实体
     */
    public GoodStock MapToEntity() throws InvocationTargetException, IllegalAccessException {
        GoodStock GoodStock = new GoodStock();
        BeanUtils.copyProperties(GoodStock, this);
        return GoodStock;
    }

}
