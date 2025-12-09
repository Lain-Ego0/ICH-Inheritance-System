package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.UserAddressDto;
import com.example.web.dto.query.UserAddressPagedInput;
import com.example.web.entity.UserAddress;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
/**
 * 收货地址功能的Service接口的定义清单
 */
public interface UserAddressService extends IService<UserAddress> {

    /**
     * 收货地址的分页查询方法接口定义
     */
    public PagedResult<UserAddressDto> List(UserAddressPagedInput input) ;
    /**
     * 收货地址的新增或者修改方法接口定义
     */
    public UserAddressDto CreateOrEdit(UserAddressDto input);

     /**
     * 获取收货地址信息
     */
    public UserAddressDto Get(UserAddressPagedInput input);
 	 /**
     * 收货地址删除
     */
    public void Delete(IdInput input);

    /**
     * 收货地址批量删除
     */
    public void BatchDelete(IdsInput input);
  
	  /**
     * 收货地址导出
     */
    public void Export(@RequestParam String query, HttpServletResponse response) throws IOException;

}
