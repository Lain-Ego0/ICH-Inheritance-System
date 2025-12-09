package com.example.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web.entity.Shop;
import org.apache.ibatis.annotations.Mapper;

/**
 * 店铺表对应的Mapper
 */
@Mapper
public interface ShopMapper  extends BaseMapper<Shop> {

}
