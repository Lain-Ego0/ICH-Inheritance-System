package com.example.web.controller;

import com.example.web.dto.GoodDto;
import com.example.web.dto.query.GoodPagedInput;
import com.example.web.mapper.GoodMapper;
import com.example.web.service.GoodService;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * 文创品控制器
 */
@RestController()
@RequestMapping("/Good")
public class GoodController {
    @Autowired()
    private GoodService GoodService;
    @Autowired()
    private GoodMapper GoodMapper;

    /**
     * 文创品分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<GoodDto> List(@RequestBody GoodPagedInput input) {
        return GoodService.List(input);
    }

    /**
     * 协同过滤推荐
     */
    @RequestMapping(value = "/GetRecommendList", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<GoodDto> GetRecommendList(@RequestBody GoodPagedInput input) {
        return GoodService.GetRecommendList(input);
    }

    /**
     * 单个文创品查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public GoodDto Get(@RequestBody GoodPagedInput input) {

        return GoodService.Get(input);
    }

    /**
     * 文创品创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public GoodDto CreateOrEdit(@RequestBody GoodDto input) throws Exception {
        return GoodService.CreateOrEdit(input);
    }

    /**
     * 文创品删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input) {
        GoodService.Delete(input);
    }

    /**
     * 文创品批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input) {
        GoodService.BatchDelete(input);
    }

    /**
     * 文创品导出
     */
    @RequestMapping(value = "/Export", method = RequestMethod.GET)
    public void Export(@RequestParam String query, HttpServletResponse response) throws IOException {
        GoodService.Export(query, response);
    }

}
