package com.example.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web.entity.CouseOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * 课程订单表对应的Mapper
 */
@Mapper
public interface CouseOrderMapper  extends BaseMapper<CouseOrder> {

}
