package com.example.web.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.web.dto.TopicDto;
import com.example.web.dto.query.TopicPagedInput;
import com.example.web.enums.AuditStatusEnum;
import com.example.web.service.TopicService;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import com.example.web.tools.dto.SelectResult;

import lombok.SneakyThrows;

/**
 * 话题控制器
 */
@RestController()
@RequestMapping("/Topic")
public class TopicController {
    @Autowired()
    private TopicService _TopicService;

    /**
     * 话题分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<TopicDto> List(@RequestBody TopicPagedInput input) {
        return _TopicService.List(input);
    }

    /**
     * 话题推荐(协同过滤算法)
     */
    @RequestMapping(value = "/RecommendTop10", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<TopicDto> RecommendTop10(@RequestBody TopicPagedInput input) {
        return _TopicService.RecommendTop10(input);
    }

    /**
     * 单个话题查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public TopicDto Get(@RequestBody TopicPagedInput input) {
        return _TopicService.Get(input);
    }

    /**
     * 话题创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public TopicDto CreateOrEdit(@RequestBody TopicDto input) throws Exception {
        return _TopicService.CreateOrEdit(input);
    }

    /**
     * 话题删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input) {
        _TopicService.Delete(input);
    }

    /**
     * 话题批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input) {
        _TopicService.BatchDelete(input);
    }

    /**
     * 话题统计
     */
    @RequestMapping(value = "/TopicAnalyseEchart", method = RequestMethod.POST)
    @SneakyThrows
    public Map<String, Object> TopicAnalyse() {
        return _TopicService.TopicAnalyse();
    }

    /**
     * 得到最近7天最高的浏览
     */
    @RequestMapping(value = "/Last7MaxTopicAnalyseEchart", method = RequestMethod.POST)
    @SneakyThrows
    private Map<String, Object> Last7MaxTopicAnalyse() {

        return _TopicService.Last7MaxTopicAnalyse();
    }

    /**
     * 得到比较靠前的发帖人(后期优化成协同过滤算法)
     */
    @RequestMapping(value = "/MostActiveTopicOfBlogger", method = RequestMethod.POST)
    @SneakyThrows
    private Map<String, Object> MostActiveTopicOfBlogger() {
        return _TopicService.MostActiveTopicOfBlogger();
    }

    /**
     * 查询我的话题一些信息(文章 收藏人数 阅读人数)
     */
    @RequestMapping(value = "/MyTopicInfoCard", method = RequestMethod.POST)
    @SneakyThrows
    private Map<String, Object> MyTopicInfoCard(@RequestBody IdInput input) {
        return _TopicService.MyTopicInfoCard(input);
    }

    /**
     * 审核状态枚举接口
     */
    @RequestMapping(value = "/AuditStatus", method = RequestMethod.POST)
    public PagedResult<SelectResult> AuditStatus() throws InvocationTargetException, IllegalAccessException {

        var rs = Arrays.stream(AuditStatusEnum.values())
                .map(n -> new SelectResult(n.toString(), n.name(), Integer.toString(n.index()), "")).toList();
        return PagedResult.GetInstance(rs, rs.stream().count());
    }

    /**
     * 得到话题的看板统计数据
     */
    @RequestMapping(value = "/TopicDataBoard", method = RequestMethod.POST)
    @SneakyThrows
    private Map<String, Object> TopicDataBoard() {
        return _TopicService.TopicDataBoard();
    }

    /**
     * 话题综合分析 - 返回多维度统计数据用于前端可视化展示
     *
     */
    @RequestMapping(value = "/TopicSynthesizeAnalyse", method = RequestMethod.POST)
    @SneakyThrows
    private Map<String, Object> TopicSynthesizeAnalyse() {
        return _TopicService.TopicSynthesizeAnalyse();
    }

    /**
     * 基于内容的算法推荐
     */
    @RequestMapping(value = "/ContentRecommendTop10", method = RequestMethod.POST)
    @SneakyThrows
    private PagedResult<TopicDto> ContentRecommendTop10(@RequestBody TopicPagedInput input) {
        return _TopicService.ContentRecommendTop10(input);
    }
}