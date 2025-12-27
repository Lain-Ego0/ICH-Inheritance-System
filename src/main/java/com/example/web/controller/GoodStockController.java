package com.example.web.controller;

import com.example.web.dto.GoodStockDto;
import com.example.web.dto.query.GoodStockPagedInput;
import com.example.web.mapper.GoodStockMapper;
import com.example.web.service.GoodStockService;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 库存记录控制器
 */
@RestController()
@RequestMapping("/GoodStock")
public class GoodStockController {
    @Autowired()
    private  GoodStockService GoodStockService;
    @Autowired()
    private GoodStockMapper GoodStockMapper;
    /**
     * 库存记录分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<GoodStockDto> List(@RequestBody GoodStockPagedInput input)  {
        return GoodStockService.List(input);
    }
     /**
     * 单个库存记录查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public GoodStockDto Get(@RequestBody GoodStockPagedInput input) {

        return GoodStockService.Get(input);
    }
  
    /**
     * 库存记录创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public GoodStockDto CreateOrEdit(@RequestBody GoodStockDto input) throws Exception {
        return GoodStockService.CreateOrEdit(input);
    }
    /**
     * 库存记录删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input)
    {
        GoodStockService.Delete(input);
    }

    /**
     * 库存记录批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input)
    {
        GoodStockService.BatchDelete(input);
    }
  

 
}
