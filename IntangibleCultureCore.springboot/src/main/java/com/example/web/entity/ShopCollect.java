package com.example.web.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.web.dto.ShopCollectDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
/**
 * 店铺收藏表
 */
@Data
@TableName("`ShopCollect`")
public class ShopCollect extends BaseEntity {

      
    /**
     * 店铺
     */  
    @JsonProperty("ShopId")
    @TableField(value="ShopId",updateStrategy = FieldStrategy.IGNORED)
    private Integer ShopId;          
      
    /**
     * 收藏人
     */  
    @JsonProperty("CollectUserId")
    @TableField(value="CollectUserId",updateStrategy = FieldStrategy.IGNORED)
    private Integer CollectUserId;          
  
    /**
     * 把店铺收藏实体转换成店铺收藏传输模型
     */
    public ShopCollectDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        ShopCollectDto ShopCollectDto = new ShopCollectDto();
        BeanUtils.copyProperties(ShopCollectDto,this);
        return ShopCollectDto;
    }

}
