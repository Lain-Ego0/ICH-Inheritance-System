package com.example.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.web.dto.CulturalDto;
import com.example.web.dto.query.CulturalPagedInput;
import com.example.web.mapper.CulturalMapper;
import com.example.web.service.CulturalService;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;

import lombok.SneakyThrows;

/**
 * 非遗文化控制器
 */
@RestController()
@RequestMapping("/Cultural")
public class CulturalController {
    @Autowired()
    private CulturalService CulturalService;
    @Autowired()
    private CulturalMapper CulturalMapper;

    /**
     * 非遗文化分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<CulturalDto> List(@RequestBody CulturalPagedInput input) {
        return CulturalService.List(input);
    }

    /**
     * 单个非遗文化查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public CulturalDto Get(@RequestBody CulturalPagedInput input) {

        return CulturalService.Get(input);
    }

    /**
     * 非遗文化创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public CulturalDto CreateOrEdit(@RequestBody CulturalDto input) throws Exception {
        return CulturalService.CreateOrEdit(input);
    }

    /**
     * 非遗文化删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input) {
        CulturalService.Delete(input);
    }

    /**
     * 非遗文化批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input) {
        CulturalService.BatchDelete(input);
    }

    /**
     * 基于内容进行推荐
     */
    @RequestMapping(value = "/RecommendByContent", method = RequestMethod.POST)
    public PagedResult<CulturalDto> RecommendByContent(@RequestBody CulturalPagedInput input) {
        return CulturalService.RecommendByContent(input);
    }

    /**
     * 综合分析
     */
    @RequestMapping(value = "/CulturalAnalyse", method = RequestMethod.POST)
    public Map<String, Object> CulturalAnalyse() {
        return CulturalService.CulturalAnalyse();
    }
}
