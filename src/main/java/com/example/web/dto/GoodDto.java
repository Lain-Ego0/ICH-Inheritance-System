package com.example.web.dto;

import com.example.web.entity.Good;
import com.example.web.tools.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 文创品类
 */
@Data
public class GoodDto extends BaseDto {

    /**
     * 名称
     */
    @JsonProperty("Name")
    private String Name;

    /**
     * 封面
     */
    @JsonProperty("Cover")
    private String Cover;

    /**
     * 主图
     */
    @JsonProperty("ImageUrls")
    private String ImageUrls;

    /**
     * 价格
     */
    @JsonProperty("Price")
    private Double Price;

    /**
     * 库存
     */
    @JsonProperty("Stock")
    private Double Stock;

    /**
     * 详情内容
     */
    @JsonProperty("Content")
    private String Content;

    /**
     * 所属店铺
     */
    @JsonProperty("ShopId")
    private Integer ShopId;

    /**
     * 单位
     */
    @JsonProperty("Unit")
    private String Unit;

    /**
     * 分类
     */
    @JsonProperty("GoodTypeId")
    private Integer GoodTypeId;

    @JsonProperty("GoodTypeDto")
    private GoodTypeDto GoodTypeDto;

    @JsonProperty("ShopDto")
    private ShopDto ShopDto;

    /**
     * 购买人次
     */
    @JsonProperty("BuyCount")
    private Integer BuyCount;

    /**
     * 库存下线
     */
    @JsonProperty("ReplenishmentLimit")
    private Integer ReplenishmentLimit;

    /**
     * 属性
     */
    @JsonProperty("GoodPropDtoList")
    private List<GoodPropDto> GoodPropDtoList;

    /**
     * 把文创品传输模型转换成文创品实体
     */
    public Good MapToEntity() throws InvocationTargetException, IllegalAccessException {
        Good Good = new Good();
        BeanUtils.copyProperties(Good, this);
        return Good;
    }

}
