package com.example.web.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.CulturalDto;
import com.example.web.dto.query.CulturalPagedInput;
import com.example.web.entity.Cultural;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;

/**
 * 非遗文化功能的Service接口的定义清单
 */
public interface CulturalService extends IService<Cultural> {

    /**
     * 非遗文化的分页查询方法接口定义
     */
    public PagedResult<CulturalDto> List(CulturalPagedInput input);

    /**
     * 非遗文化的新增或者修改方法接口定义
     */
    public CulturalDto CreateOrEdit(CulturalDto input);

    /**
     * 获取非遗文化信息
     */
    public CulturalDto Get(CulturalPagedInput input);

    /**
     * 非遗文化删除
     */
    public void Delete(IdInput input);

    /**
     * 非遗文化批量删除
     */
    public void BatchDelete(IdsInput input);

    /**
     * 基于内容进行推荐
     * 
     * @param input
     * @return
     */
    public PagedResult<CulturalDto> RecommendByContent(CulturalPagedInput input);

    /**
     * 综合分析
     * 
     * @return 包含多维度统计数据的HashMap
     */
    public Map<String, Object> CulturalAnalyse();
}
