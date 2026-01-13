package com.example.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web.entity.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单表对应的Mapper
 */
@Mapper
public interface OrderInfoMapper  extends BaseMapper<OrderInfo> {

}
