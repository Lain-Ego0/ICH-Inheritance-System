package com.example.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web.entity.CouseClock;
import org.apache.ibatis.annotations.Mapper;

/**
 * 课程打卡表对应的Mapper
 */
@Mapper
public interface CouseClockMapper  extends BaseMapper<CouseClock> {

}
