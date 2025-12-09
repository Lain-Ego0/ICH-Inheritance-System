package com.example.web.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.web.dto.CouseTypeDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
/**
 * 课程分类表
 */
@Data
@TableName("`CouseType`")
public class CouseType extends BaseEntity {

      
  	  /**
     * 分类
     */  
    @JsonProperty("Name")
    @TableField(value="Name",updateStrategy = FieldStrategy.IGNORED)
    private String Name;
  
    /**
     * 把课程分类实体转换成课程分类传输模型
     */
    public CouseTypeDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        CouseTypeDto CouseTypeDto = new CouseTypeDto();
        BeanUtils.copyProperties(CouseTypeDto,this);
        return CouseTypeDto;
    }

}
