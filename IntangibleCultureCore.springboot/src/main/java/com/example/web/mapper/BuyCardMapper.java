package com.example.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web.entity.BuyCard;
import org.apache.ibatis.annotations.Mapper;

/**
 * 购物车表对应的Mapper
 */
@Mapper
public interface BuyCardMapper extends BaseMapper<BuyCard> {

}
