package com.example.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web.entity.Topic;
import org.apache.ibatis.annotations.Mapper;

/**
 * 话题表对应的Mapper
 */
@Mapper
public interface TopicMapper  extends BaseMapper<Topic> {

}
