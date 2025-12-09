package com.example.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web.entity.OrderReturn;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单退货表对应的Mapper
 */
@Mapper
public interface OrderReturnMapper  extends BaseMapper<OrderReturn> {

}
