package com.example.web.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web.entity.GoodType;

/**
 * 文创品分类表对应的Mapper
 */
@Mapper
public interface GoodTypeMapper extends BaseMapper<GoodType> {

}
