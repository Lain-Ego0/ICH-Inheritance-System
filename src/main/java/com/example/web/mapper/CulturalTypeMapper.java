package com.example.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web.entity.CulturalType;
import org.apache.ibatis.annotations.Mapper;

/**
 * 非遗文化类别表对应的Mapper
 */
@Mapper
public interface CulturalTypeMapper extends BaseMapper<CulturalType> {

}
