package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.ShopDto;
import com.example.web.dto.query.ShopPagedInput;
import com.example.web.entity.Shop;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**
 * 店铺功能的Service接口的定义清单
 */
public interface ShopService extends IService<Shop> {

    /**
     * 店铺的分页查询方法接口定义
     */
    public PagedResult<ShopDto> List(ShopPagedInput input);

    /**
     * 店铺的新增或者修改方法接口定义
     */
    public ShopDto CreateOrEdit(ShopDto input);

    /**
     * 获取店铺信息
     */
    public ShopDto Get(ShopPagedInput input);

    /**
     * 店铺删除
     */
    public void Delete(IdInput input);

    /**
     * 店铺批量删除
     */
    public void BatchDelete(IdsInput input);

    /**
     * 店铺导出
     */
    public void Export(@RequestParam String query, HttpServletResponse response) throws IOException;

    /**
     * 商家注册
     */
    public ShopDto ShopRegister(ShopDto input);

    /**
     * 店铺审核
     */
    @SneakyThrows
    ShopDto Audit(ShopDto input);
}
