package com.example.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web.entity.OrderComment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单评价表对应的Mapper
 */
@Mapper
public interface OrderCommentMapper  extends BaseMapper<OrderComment> {

}
