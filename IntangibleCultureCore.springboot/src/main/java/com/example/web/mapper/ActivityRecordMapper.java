package com.example.web.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web.entity.*;
import org.apache.ibatis.annotations.Mapper;

/**
 * 活动登记表对应的Mapper
 */
@Mapper
public interface ActivityRecordMapper  extends BaseMapper<ActivityRecord> {

}
