package com.example.web.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.web.dto.CulturalTypeDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 非遗文化类别表
 */
@Data
@TableName("`CulturalType`")
public class CulturalType extends BaseEntity {

      
  	  /**
     * 名称
     */  
    @JsonProperty("Name")
    @TableField(value="Name",updateStrategy = FieldStrategy.IGNORED)
    private String Name;
  
    /**
     * 把非遗文化类别实体转换成非遗文化类别传输模型
     */
    public CulturalTypeDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        CulturalTypeDto CulturalTypeDto = new CulturalTypeDto();
       
        BeanUtils.copyProperties(CulturalTypeDto,this);
       
        return CulturalTypeDto;
    }

}
