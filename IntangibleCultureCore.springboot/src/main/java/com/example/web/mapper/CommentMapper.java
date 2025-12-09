package com.example.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评论表对应的Mapper
 */
@Mapper
public interface CommentMapper  extends BaseMapper<Comment> {

}
