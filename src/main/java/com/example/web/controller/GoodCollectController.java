package com.example.web.controller;

import com.example.web.dto.GoodCollectDto;
import com.example.web.dto.query.GoodCollectPagedInput;
import com.example.web.mapper.GoodCollectMapper;
import com.example.web.service.GoodCollectService;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * 文创品收藏控制器
 */
@RestController()
@RequestMapping("/GoodCollect")
public class GoodCollectController {
    @Autowired()
    private GoodCollectService GoodCollectService;
    @Autowired()
    private GoodCollectMapper GoodCollectMapper;

    /**
     * 文创品收藏分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<GoodCollectDto> List(@RequestBody GoodCollectPagedInput input) {
        return GoodCollectService.List(input);
    }

    /**
     * 单个文创品收藏查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public GoodCollectDto Get(@RequestBody GoodCollectPagedInput input) {

        return GoodCollectService.Get(input);
    }

    /**
     * 文创品收藏创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public GoodCollectDto CreateOrEdit(@RequestBody GoodCollectDto input) throws Exception {
        return GoodCollectService.CreateOrEdit(input);
    }

    /**
     * 文创品收藏删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input) {
        GoodCollectService.Delete(input);
    }

    /**
     * 文创品收藏批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input) {
        GoodCollectService.BatchDelete(input);
    }

    /**
     * 文创品收藏导出
     */
    @RequestMapping(value = "/Export", method = RequestMethod.GET)
    public void Export(@RequestParam String query, HttpServletResponse response) throws IOException {
        GoodCollectService.Export(query, response);
    }

}
