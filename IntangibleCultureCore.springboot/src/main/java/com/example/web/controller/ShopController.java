package com.example.web.controller;

import com.example.web.dto.ShopDto;
import com.example.web.dto.query.ShopPagedInput;
import com.example.web.mapper.ShopMapper;
import com.example.web.service.ShopService;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * 店铺控制器
 */
@RestController()
@RequestMapping("/Shop")
public class ShopController {
    @Autowired()
    private ShopService ShopService;
    @Autowired()
    private ShopMapper ShopMapper;

    /**
     * 店铺分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<ShopDto> List(@RequestBody ShopPagedInput input) {
        return ShopService.List(input);
    }

    /**
     * 单个店铺查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public ShopDto Get(@RequestBody ShopPagedInput input) {

        return ShopService.Get(input);
    }

    /**
     * 店铺创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public ShopDto CreateOrEdit(@RequestBody ShopDto input) throws Exception {
        return ShopService.CreateOrEdit(input);
    }

    /**
     * 店铺删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input) {
        ShopService.Delete(input);
    }

    /**
     * 店铺批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input) {
        ShopService.BatchDelete(input);
    }

    /**
     * 店铺导出
     */
    @RequestMapping(value = "/Export", method = RequestMethod.GET)
    public void Export(@RequestParam String query, HttpServletResponse response) throws IOException {
        ShopService.Export(query, response);
    }

    /**
     * 店铺注册
     */
    @RequestMapping(value = "/ShopRegister", method = RequestMethod.POST)
    public ShopDto ShopRegister(@RequestBody ShopDto input) throws Exception {
        return ShopService.ShopRegister(input);
    }

    /**
     * 店铺审核
     */
    @RequestMapping(value = "/Audit", method = RequestMethod.POST)
    public ShopDto Audit(@RequestBody ShopDto input) throws Exception {
        return ShopService.Audit(input);
    }


}
