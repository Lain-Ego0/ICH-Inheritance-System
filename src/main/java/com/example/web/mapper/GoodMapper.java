package com.example.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web.entity.Good;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文创品表对应的Mapper
 */
@Mapper
public interface GoodMapper extends BaseMapper<Good> {

}
