package com.example.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web.entity.OrderDet;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单明细表对应的Mapper
 */
@Mapper
public interface OrderDetMapper  extends BaseMapper<OrderDet> {

}
