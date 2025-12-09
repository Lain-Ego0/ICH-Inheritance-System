package com.example.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web.entity.CouseComment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 课程评价表对应的Mapper
 */
@Mapper
public interface CouseCommentMapper  extends BaseMapper<CouseComment> {

}
