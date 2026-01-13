package com.example.web.controller;

import com.example.web.dto.UserAddressDto;
import com.example.web.dto.query.UserAddressPagedInput;
import com.example.web.mapper.UserAddressMapper;
import com.example.web.service.UserAddressService;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
/**
 * 收货地址控制器
 */
@RestController()
@RequestMapping("/UserAddress")
public class UserAddressController {
    @Autowired()
    private  UserAddressService  UserAddressService;
    @Autowired()
    private UserAddressMapper  UserAddressMapper;
    /**
     * 收货地址分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<UserAddressDto> List(@RequestBody UserAddressPagedInput input)  {
        return  UserAddressService.List(input);
    }
     /**
     * 单个收货地址查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public UserAddressDto Get(@RequestBody UserAddressPagedInput input) {

        return  UserAddressService.Get(input);
    }
  
    /**
     * 收货地址创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public UserAddressDto CreateOrEdit(@RequestBody UserAddressDto input) throws Exception {
        return  UserAddressService.CreateOrEdit(input);
    }
    /**
     * 收货地址删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input)
    {
        UserAddressService.Delete(input);
    }

    /**
     * 收货地址批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input)
    {
        UserAddressService.BatchDelete(input);
    }
	  /**
     * 收货地址导出
     */
    @RequestMapping(value = "/Export", method = RequestMethod.GET)
    public void Export(@RequestParam String query, HttpServletResponse response) throws IOException {
      UserAddressService.Export(query,response);
    }
  

 
}
