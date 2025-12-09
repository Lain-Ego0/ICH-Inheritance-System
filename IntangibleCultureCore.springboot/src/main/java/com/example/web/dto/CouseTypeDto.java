package com.example.web.dto;

import com.example.web.entity.CouseType;
import com.example.web.tools.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 课程分类类
 */
@Data
public class CouseTypeDto extends BaseDto
{

    
     
    /**
     * 分类
     */ 
    @JsonProperty("Name")
    private String Name;

 	 /**
     * 把课程分类传输模型转换成课程分类实体
     */
    public CouseType MapToEntity() throws InvocationTargetException, IllegalAccessException {
        CouseType CouseType= new CouseType();
        BeanUtils.copyProperties(CouseType,this);
        return CouseType;
    }

}
