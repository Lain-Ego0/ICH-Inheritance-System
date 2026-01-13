package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.TopicDto;
import com.example.web.dto.query.TopicPagedInput;
import com.example.web.entity.Topic;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import lombok.SneakyThrows;

import java.util.Map;


/**
 * 话题功能的Service接口的定义清单
 */
public interface TopicService extends IService<Topic> {

    @SneakyThrows
    PagedResult<TopicDto> RecommendTop10(TopicPagedInput input);

    /**
     * 话题的分页查询方法接口定义
     */
    public PagedResult<TopicDto> List(TopicPagedInput input);

    /**
     * 话题的新增或者修改方法接口定义
     */
    public TopicDto CreateOrEdit(TopicDto input);

    /**
     * 获取话题信息
     */
    public TopicDto Get(TopicPagedInput input);

    /**
     * 话题删除
     */
    public void Delete(IdInput input);

    /**
     * 话题批量删除
     */
    public void BatchDelete(IdsInput input);

    /**
     * 话题统计
     */
    public Map<String, Object> TopicAnalyse();

    /**
     * 得到最近7天最高的浏览
     */
    public Map<String, Object> Last7MaxTopicAnalyse();

    /**
     * 得到比较靠前的发帖人(后期优化成协同过滤算法)
     */
    public Map<String, Object> MostActiveTopicOfBlogger();


    /**
     * 查询我的话题一些信息(文章 收藏人数  阅读人数)
     */
    public Map<String, Object> MyTopicInfoCard(IdInput input);
    /**
     * 得到话题的看板统计数据
     */
    public Map<String, Object> TopicDataBoard();


    /**
     * 话题综合分析 - 返回多维度统计数据用于前端可视化展示
     *
     */
    public Map<String, Object> TopicSynthesizeAnalyse();



    /**
     * 基于内容的算法推荐
     *
     * @param input
     * @return
     */
    public PagedResult<TopicDto> ContentRecommendTop10(TopicPagedInput input);
}
