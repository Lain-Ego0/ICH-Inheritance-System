package com.example.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web.entity.GoodStock;
import org.apache.ibatis.annotations.Mapper;

/**
 * 库存记录表对应的Mapper
 */
@Mapper
public interface GoodStockMapper extends BaseMapper<GoodStock> {

}
