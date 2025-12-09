package com.example.web.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web.entity.GoodProp;

/**
 * 文创品属性表对应的Mapper
 */
@Mapper
public interface GoodPropMapper extends BaseMapper<GoodProp> {

}
