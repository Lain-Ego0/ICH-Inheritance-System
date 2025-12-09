package com.example.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web.entity.TopicCollect;
import org.apache.ibatis.annotations.Mapper;

/**
 * 话题收藏表对应的Mapper
 */
@Mapper
public interface TopicCollectMapper  extends BaseMapper<TopicCollect> {

}
