package com.example.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web.entity.UserAddress;
import org.apache.ibatis.annotations.Mapper;

/**
 * 收货地址表对应的Mapper
 */
@Mapper
public interface UserAddressMapper  extends BaseMapper<UserAddress> {

}
