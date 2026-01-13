package com.example.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web.entity.TopicType;
import org.apache.ibatis.annotations.Mapper;

/**
 * 话题类型表对应的Mapper
 */
@Mapper
public interface TopicTypeMapper extends BaseMapper<TopicType> {

}
