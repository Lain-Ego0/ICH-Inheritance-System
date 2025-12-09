package com.example.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web.entity.CommentLove;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评论点赞记录表对应的Mapper
 */
@Mapper
public interface CommentLoveMapper  extends BaseMapper<CommentLove> {

}
