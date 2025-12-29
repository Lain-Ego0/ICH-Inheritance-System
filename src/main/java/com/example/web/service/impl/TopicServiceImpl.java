package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.AppUserDto;
import com.example.web.dto.CommentDto;
import com.example.web.dto.TopicDto;
import com.example.web.dto.TopicTypeDto;
import com.example.web.dto.query.TopicPagedInput;
import com.example.web.entity.*;
import com.example.web.enums.AuditStatusEnum;
import com.example.web.mapper.*;
import com.example.web.service.TopicService;
import com.example.web.tools.BaseContext;
import com.example.web.tools.CollaborativeFiltering;
import com.example.web.tools.Extension;
import com.example.web.tools.dto.*;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 话题功能实现类
 */
@Service
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements TopicService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper _AppUserMapper;
    /**
     * 操作数据库的Topic表mapper对象
     */
    @Autowired
    private TopicMapper _TopicMpper;

    @Autowired
    private TopicTypeMapper _TopicTypeMapper;

    @Autowired
    private CommentMapper _CommentMapper;

    /**
     * 操作数据库的CommentLove表mapper对象
     */
    @Autowired
    private CommentLoveMapper _CommentLoveMpper;
    /**
     * 操作数据库的TopicRecord表mapper对象
     */
    @Autowired
    private TopicRecordMapper _TopicRecordMpper;

    @Autowired()
    private TopicCollectMapper _TopicCollectMapper;

    /**
     * 提取话题的特征向量
     *
     * @param topic 话题
     * @return 特征向量 (关键词 -> 权重)
     */
    private Map<String, Double> extractFeatureVector(Topic topic) {
        Map<String, Double> featureVector = new HashMap<>();

        // 处理标题 (标题关键词权重更高)
        if (topic.getTitle() != null) {
            String[] titleWords = topic.getTitle().split("\\s+");
            for (String word : titleWords) {
                word = word.toLowerCase().trim();
                if (word.length() > 1) {
                    featureVector.put(word, featureVector.getOrDefault(word, 0.0) + 2.0); // 标题词权重加倍
                }
            }
        }

        // 处理内容
        if (topic.getContent() != null) {
            // 移除HTML标签
            String content = topic.getContent().replaceAll("<[^>]*>", "");
            String[] contentWords = content.split("\\s+");
            for (String word : contentWords) {
                word = word.toLowerCase().trim();
                if (word.length() > 2) { // 过滤短词
                    featureVector.put(word, featureVector.getOrDefault(word, 0.0) + 1.0);
                }
            }
        }

        // 归一化特征向量
        double sum = featureVector.values().stream().mapToDouble(Double::doubleValue).sum();
        if (sum > 0) {
            for (String key : featureVector.keySet()) {
                featureVector.put(key, featureVector.get(key) / sum);
            }
        }

        return featureVector;
    }

    /**
     * 计算两个特征向量的余弦相似度
     *
     * @param vector1     特征向量1
     * @param vector2     特征向量2
     * @param allKeywords 所有关键词集合
     * @return 余弦相似度
     */
    private double calculateCosineSimilarity(Map<String, Double> vector1, Map<String, Double> vector2,
                                             Set<String> allKeywords) {
        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for (String keyword : allKeywords) {
            double val1 = vector1.getOrDefault(keyword, 0.0);
            double val2 = vector2.getOrDefault(keyword, 0.0);

            dotProduct += val1 * val2;
            norm1 += val1 * val1;
            norm2 += val2 * val2;
        }

        if (norm1 == 0 || norm2 == 0) {
            return 0.0;
        }

        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

    /**
     * 协同过滤算法推荐(根据浏览记录获取资讯)
     */
    @SneakyThrows
    @Override
    public PagedResult<TopicDto> RecommendTop10(TopicPagedInput input) {
        return RecommendCollaborativeFilteringTopic(input);
    }

    /**
     * 协同算法推荐
     */
    private PagedResult<TopicDto> RecommendCollaborativeFilteringTopic(TopicPagedInput input) {
        CurrentUserDto currentUserDto = BaseContext.getCurrentUserDto();
        if (currentUserDto != null && currentUserDto.getUserId() > 0) {
            Integer userId = currentUserDto.getUserId();
            CollaborativeFiltering collaborativeFiltering = new CollaborativeFiltering();
            // 查询所有订单明细
            List<TopicRecord> topicRecords = _TopicRecordMpper.selectList(null);
            for (TopicRecord topicRecord : topicRecords) {
                // 每个商品购买次数算一个点数
                collaborativeFiltering.AddUserRating(topicRecord.getCreatorId(), topicRecord.getTopicId(), 1.00);
            }
            List<UserCalculateCosineSimilarity> userCalculateCosineSimilarityList = collaborativeFiltering
                    .GetUserCalculateCosineSimilarity(userId);
            // 查询对应的商品
            List<Integer> topicIds = userCalculateCosineSimilarityList.stream().flatMap(x -> x.getItemIds().stream())
                    .toList();
            if (topicIds.size() == 0) {
                return PagedResult.GetEmptyInstance();
            }
            input.setTopicIds(topicIds);
            return List(input);
        }
        return PagedResult.GetEmptyInstance();
    }

    /**
     * 处理明细
     */
    private void ProcessTopicDto(TopicDto item) throws InvocationTargetException, IllegalAccessException {
        // 查询出关联的创建用户信息
        AppUserDto CreatorAppUserDTO = new AppUserDto();
        AppUser CreatorAppUserEntity = _AppUserMapper
                .selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getCreatorId())).stream()
                .findFirst().orElse(new AppUser());
        if (CreatorAppUserEntity != null) {
            BeanUtils.copyProperties(CreatorAppUserDTO, CreatorAppUserEntity);
            item.setCreatorAppUserDto(CreatorAppUserDTO);
        }
        // 查询关联的Comment表信息
        List<Comment> CommentEntitys = _CommentMapper
                .selectList(Wrappers.<Comment>lambdaQuery().eq(Comment::getRelativeId, item.getId()));
        List<CommentDto> CommentDTOS = Extension.copyBeanList(CommentEntitys, CommentDto.class);
        item.setCommentDtos(CommentDTOS);

        // 查询出关联的TopicType表信息
        TopicTypeDto TopicTypeDTO = new TopicTypeDto();
        TopicType TopicTypeEntity = _TopicTypeMapper
                .selectList(Wrappers.<TopicType>lambdaQuery().eq(TopicType::getId, item.getTopicTypeId())).stream()
                .findFirst().orElse(new TopicType());
        if (TopicTypeEntity != null) {
            BeanUtils.copyProperties(TopicTypeDTO, TopicTypeEntity);
            item.setTopicTypeDto(TopicTypeDTO);
        }

    }

    /**
     * 话题分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<TopicDto> List(TopicPagedInput input) {

        // 声明一个支持话题查询的(拉姆达)表达式
        LambdaQueryWrapper<Topic> queryWrapper = Wrappers.<Topic>lambdaQuery()
                .eq(input.getId() != null, Topic::getId, input.getId())
                .eq(input.getCreatorId() != null, Topic::getCreatorId, input.getCreatorId());

        // 如果前端搜索传入Title不为空,则进行精确查询
        if (Extension.isNotNullOrEmpty(input.getTitle())) {
            queryWrapper = queryWrapper.like(Topic::getTitle, input.getTitle());
        }
        // 如果前端搜索传入TopicTypeId不为空,则进行精确查询
        if (input.getTopicTypeId() != null) {
            queryWrapper = queryWrapper.eq(Topic::getTopicTypeId, input.getTopicTypeId());
        }

        // 如果前端搜索传入AuditStatus不为空,则进行精确查询
        if (input.getAuditStatus() != null) {
            queryWrapper = queryWrapper.eq(Topic::getAuditStatus, input.getAuditStatus());
        }
        // 如果前端搜索传入IsRecommand不为空,则进行精确查询
        if (input.getIsRecommand() != null) {
            queryWrapper = queryWrapper.eq(Topic::getIsRecommand, input.getIsRecommand());
        }

        if (input.getIsHot() != null && input.getIsHot()) {
            queryWrapper = queryWrapper.orderByDesc(Topic::getViewCount);
        } else {

            queryWrapper = queryWrapper.orderByDesc(Topic::getCreationTime);
        }

        // 如果传入了关键词
        if (Extension.isNotNullOrEmpty(input.getKeyWord())) {
            queryWrapper = queryWrapper.and(
                    q -> q.like(Topic::getTitle, input.getKeyWord()).or().like(Topic::getContent, input.getKeyWord()));
        }

        // 构建一个分页查询的model
        Page<Topic> page = new Page<>(input.getPage(), input.getLimit());

        // 从数据库进行分页查询获取话题数据
        IPage<Topic> pageRecords = _TopicMpper.selectPage(page, queryWrapper);

        // 获取所有满足条件的数据行数
        Long totalCount = _TopicMpper.selectCount(queryWrapper);

        // 把Topic实体转换成Topic传输模型
        List<TopicDto> items = Extension.copyBeanList(pageRecords.getRecords(), TopicDto.class);
        for (TopicDto item : items) {

            ProcessTopicDto(item);

        }

        // 返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个话题查询
     */
    @SneakyThrows
    @Override
    public TopicDto Get(TopicPagedInput input) {
        if (input.getId() == null) {
            return new TopicDto();
        }
        PagedResult<TopicDto> pagedResult = List(input);
        UpdateViewCount(input.getId());
        return pagedResult.getTotalCount() > 0 ? pagedResult.getItems().stream().findFirst().get() : new TopicDto();
    }

    /**
     * 话题删除
     */
    @Override
    public void Delete(IdInput input) {

        Topic entity = _TopicMpper.selectById(input.getId());

        _TopicMpper.deleteById(entity);

        _TopicCollectMapper.delete(Wrappers.<TopicCollect>lambdaQuery().eq(TopicCollect::getTopicId, input.getId()));

        _TopicRecordMpper.delete(Wrappers.<TopicRecord>lambdaQuery().eq(TopicRecord::getTopicId, input.getId()));

    }

    /**
     * 话题批量删除
     */
    @Override
    public void BatchDelete(IdsInput input) {

        for (Integer id : input.getIds()) {
            IdInput idInput = new IdInput();
            idInput.setId(id);
            Delete(idInput);
        }
    }

    /**
     * 话题创建或者修改
     */
    @SneakyThrows
    @Override
    public TopicDto CreateOrEdit(TopicDto input) {

        // 声明一个话题实体
        Topic Topic = input.MapToEntity();

        saveOrUpdate(Topic);

        // 把传输模型返回给前端
        return Topic.MapToDto();
    }

    /**
     * 修改话题浏览次数
     */
    @SneakyThrows
    private void UpdateViewCount(Integer id) {

        Topic topic = _TopicMpper.selectById(id);
        if (topic != null) {
            if (topic.getViewCount() == null) {
                topic.setViewCount(0);
            }
            topic.setViewCount(topic.getViewCount() + 1);
            _TopicMpper.updateById(topic);
        }
    }

    /**
     * 话题统计
     */
    @Override
    public Map<String, Object> TopicAnalyse() {

        List<Topic> list = _TopicMpper
                .selectList(Wrappers.<Topic>lambdaQuery().eq(Topic::getAuditStatus, AuditStatusEnum.审核通过.index()))
                .stream().toList();

        // 得到话题类型
        List<TopicType> topicTypes = _TopicTypeMapper.selectList(null).stream().toList();
        List<TopicRecord> topicRecords = _TopicRecordMpper.selectList(null).stream().toList();

        // 得到最近7天的浏览记录
        topicRecords = topicRecords.stream()
                .filter(x -> x.getCreationTime().toLocalDate().isAfter(LocalDate.now().minusDays(7))).toList();

        Map<String, Object> result = new HashMap<>();
        // 得到最近7天的日期
        List<String> dates = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            dates.add(Extension.GetWeek(LocalDate.now().minusDays(i)));
        }
        result.put("week", dates);

        List<Map<String, Object>> allDataList = new ArrayList<>();
        // 循环话题类型
        for (TopicType topicType : topicTypes) {
            Map<String, Object> data = new HashMap<>();
            data.put("name", topicType.getName());
            // 得到对应的话题编号
            List<Integer> topicIds = list.stream().filter(x -> x.getTopicTypeId() == topicType.getId())
                    .map(x -> x.getId()).toList();
            // 得到话题所有的浏览记录
            List<TopicRecord> topicRecords1 = topicRecords.stream().filter(x -> topicIds.contains(x.getTopicId()))
                    .toList();

            ArrayList<Integer> counts = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                // 得到当天的浏览记录
                int day = i;
                List<TopicRecord> currentTopicRecords = topicRecords1.stream()
                        .filter(x -> x.getCreationTime().toLocalDate().equals(LocalDate.now().minusDays(day))).toList();
                counts.add(currentTopicRecords.size());
            }
            data.put("values", counts);

            allDataList.add(data);
        }
        result.put("datas", allDataList);
        return result;
    }

    /**
     * 得到最近7天最高的浏览
     */
    @Override
    public Map<String, Object> Last7MaxTopicAnalyse() {

        List<Topic> list = _TopicMpper
                .selectList(Wrappers.<Topic>lambdaQuery().eq(Topic::getAuditStatus, AuditStatusEnum.审核通过.index()))
                .stream().toList();

        List<TopicRecord> topicRecords = _TopicRecordMpper.selectList(Wrappers.<TopicRecord>lambdaQuery()
                .isNotNull(TopicRecord::getTopicId).isNotNull(TopicRecord::getUserId)).stream().toList();
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> allDataList = new ArrayList<>();
        // 循环所有话题
        for (Topic topic : list) {
            Map<String, Object> data = new HashMap<>();
            data.put("name", topic.getTitle());

            List<TopicRecord> currentTopicRecords = topicRecords.stream()
                    .filter(x -> x.getTopicId().equals(topic.getId())).toList();

            data.put("values", currentTopicRecords.size());

            allDataList.add(data);
        }
        // 从大到小排序 取前10个
        allDataList = allDataList.stream().sorted((x, y) -> {
            return (int) y.get("values") - (int) x.get("values");
        }).toList();
        allDataList = allDataList.stream().limit(10).toList();

        result.put("datas", allDataList);
        return result;
    }

    /**
     * 得到比较靠前的发帖人(后期优化成协同过滤算法)
     */
    @Override
    public Map<String, Object> MostActiveTopicOfBlogger() {

        CurrentUserDto currentUserDto = BaseContext.getCurrentUserDto();
        var map = new HashMap<String, Object>();
        // 得到所有的话题
        List<Topic> topics = _TopicMpper
                .selectList(Wrappers.<Topic>lambdaQuery().eq(Topic::getAuditStatus, AuditStatusEnum.审核通过.index()))
                .stream().toList();
        // 得到所有的话题浏览记录
        List<TopicRecord> topicRecords = _TopicRecordMpper.selectList(null).stream().toList();
        // 得到所有的话题收藏记录
        List<TopicCollect> topicCollects = _TopicCollectMapper.selectList(null).stream().toList();

        // 得到为用户角色的列表
        List<AppUser> users = _AppUserMapper.selectList(null).stream().toList();

        // 发帖数量来决定它的活跃程度
        // 得到最活跃的话题人
        List<Map<String, Object>> mostActiveTopicOfBlogger = new ArrayList<>();
        for (AppUser user : users) {
            if (currentUserDto.getUserId() != null && currentUserDto.getUserId() == user.getId()) {
                continue;
            }
            Map<String, Object> data = new HashMap<>();
            data.put("name", user.getName());
            // 得到对应的话题编号
            List<Integer> topicIds = topics.stream().filter(x -> x.getCreatorId() == user.getId()).map(x -> x.getId())
                    .toList();

            // 得到它的最新发的话题
            List<Topic> topicss = topics.stream()
                    .filter(x -> topicIds.contains(x.getId()) && x.getAuditStatus() == AuditStatusEnum.审核通过.index())
                    .toList();
            data.put("topicCount", topicss.stream().count());
            // 按创建时间进行倒叙
            topicss = topicss.stream().sorted((x, y) -> {
                return y.getCreationTime().compareTo(x.getCreationTime());
            }).toList();
            // 取前3个
            topicss = topicss.stream().limit(3).toList();
            data.put("topics", topicss);
            // 得到话题所有的浏览记录
            List<TopicRecord> topicRecords1 = topicRecords.stream().filter(x -> topicIds.contains(x.getTopicId()))
                    .toList();
            // 得到话题所有的收藏记录
            List<TopicCollect> topicCollects1 = topicCollects.stream().filter(x -> topicIds.contains(x.getTopicId()))
                    .toList();
            data.put("readCount", topicRecords1.size());
            data.put("collectCount", topicCollects1.size());
            data.put("userInfo", user);

            if (topicRecords1.size() > 10 || topicCollects1.size() > 10) {
                mostActiveTopicOfBlogger.add(data);
            }

        }
        // 根据发帖数进行排序
        mostActiveTopicOfBlogger = mostActiveTopicOfBlogger.stream().sorted((x, y) -> {
            return (int) y.get("readCount") - (int) x.get("readCount");
        }).toList();
        // top10个
        mostActiveTopicOfBlogger = mostActiveTopicOfBlogger.stream().limit(10).toList();

        map.put("datas", mostActiveTopicOfBlogger);
        return map;
    }

    /**
     * 查询我的话题一些信息(文章 收藏人数 阅读人数)
     */
    @Override
    public Map<String, Object> MyTopicInfoCard(IdInput input) {

        var map = new HashMap<String, Object>();

        Integer userId = input.getId();
        // 得到我的审核过了的文章的ids
        var topicIds = _TopicMpper.selectList(null).stream()
                .filter(x -> x.getAuditStatus() == AuditStatusEnum.审核通过.index() && x.getCreatorId().equals(userId))
                .toList().stream().map(x -> x.getId()).toList();

        map.put("articleCount", topicIds.size());
        // 得到我的文章总阅读人数
        var readCount = _TopicRecordMpper.selectList(null).stream().filter(x -> topicIds.contains(x.getTopicId()))
                .toList().size();
        map.put("readCount", readCount);
        // 得到我的文章总收藏人数
        var collectCount = _TopicCollectMapper.selectList(null).stream().filter(x -> topicIds.contains(x.getTopicId()))
                .toList().size();
        map.put("collectCount", collectCount);
        return map;

    }

    /**
     * 得到话题的看板统计数据
     */
    @Override
    public Map<String, Object> TopicDataBoard() {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        // 查询所有的话题数量
        long topicCount = _TopicMpper.selectCount(null);
        stringObjectHashMap.put("topicCount", topicCount);
        // 查询所有话题的浏览量
        List<TopicRecord> topicRecords = _TopicRecordMpper.selectList(null);
        long viewCount = topicRecords.size();
        stringObjectHashMap.put("viewCount", viewCount);
        // 查询所有带审核的话题数量
        long auditTopicCount = _TopicMpper
                .selectList(Wrappers.<Topic>lambdaQuery().eq(Topic::getAuditStatus, AuditStatusEnum.待审核.index()))
                .size();
        stringObjectHashMap.put("auditTopicCount", auditTopicCount);
        // 查询所有审核通过的话题数量
        long auditPassTopicCount = _TopicMpper
                .selectList(Wrappers.<Topic>lambdaQuery().eq(Topic::getAuditStatus, AuditStatusEnum.审核通过.index()))
                .size();
        stringObjectHashMap.put("auditPassTopicCount", auditPassTopicCount);
        // 查询所有审核失败的话题数量
        long auditFailTopicCount = _TopicMpper
                .selectList(Wrappers.<Topic>lambdaQuery().eq(Topic::getAuditStatus, AuditStatusEnum.审核失败.index()))
                .size();
        stringObjectHashMap.put("auditFailTopicCount", auditFailTopicCount);
        // 查询话题总评论数
        int topicCommentSize = _CommentMapper.selectList(Wrappers.<Comment>lambdaQuery().eq(Comment::getType, "Topic"))
                .size();
        stringObjectHashMap.put("topicCommentSize", topicCommentSize);
        // 查询话题评论总点赞数
        int topicLoveSize = _CommentLoveMpper
                .selectList(Wrappers.<CommentLove>lambdaQuery().eq(CommentLove::getType, "Topic")).size();
        stringObjectHashMap.put("topicLoveSize", topicLoveSize);
        return stringObjectHashMap;
    }

    /**
     * 话题综合分析 - 返回多维度统计数据用于前端可视化展示
     *
     */
    @Override
    @SneakyThrows
    public Map<String, Object> TopicSynthesizeAnalyse() {
        Map<String, Object> result = new HashMap<>();

        // 1. 话题类型分布统计
        List<TopicType> topicTypes = _TopicTypeMapper.selectList(null);
        List<Map<String, Object>> typeDistribution = new ArrayList<>();
        for (TopicType type : topicTypes) {
            Map<String, Object> typeData = new HashMap<>();
            long count = _TopicMpper.selectCount(
                    Wrappers.<Topic>lambdaQuery()
                            .eq(Topic::getTopicTypeId, type.getId())
                            .eq(Topic::getAuditStatus, AuditStatusEnum.审核通过.index()));
            typeData.put("name", type.getName());
            typeData.put("value", count);
            typeDistribution.add(typeData);
        }
        result.put("typeDistribution", typeDistribution);

        // 2. 最近30天话题发布趋势
        List<Map<String, Object>> trendData = new ArrayList<>();
        for (int i = 29; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            long count = _TopicMpper.selectCount(
                    Wrappers.<Topic>lambdaQuery()
                            .apply("date(CreationTime) = {0}", date)
                            .eq(Topic::getAuditStatus, AuditStatusEnum.审核通过.index()));
            Map<String, Object> dayData = new HashMap<>();
            dayData.put("date", date.toString());
            dayData.put("count", count);
            trendData.add(dayData);
        }
        result.put("trendData", trendData);

        // 3. 用户发帖活跃度排行（Top 10）
        List<Map<String, Object>> userActivity = new ArrayList<>();
        List<AppUser> users = _AppUserMapper.selectList(null);
        for (AppUser user : users) {
            Map<String, Object> userData = new HashMap<>();
            // 发帖数
            long topicCount = _TopicMpper.selectCount(
                    Wrappers.<Topic>lambdaQuery()
                            .eq(Topic::getCreatorId, user.getId())
                            .eq(Topic::getAuditStatus, AuditStatusEnum.审核通过.index()));
            // 获取评论数
            long commentCount = _CommentMapper.selectCount(
                    Wrappers.<Comment>lambdaQuery()
                            .eq(Comment::getCreatorId, user.getId()));
            userData.put("userId", user.getId());
            userData.put("userName", user.getName());
            userData.put("topicCount", topicCount);
            userData.put("commentCount", commentCount);
            userData.put("activityScore", topicCount * 2 + commentCount); // 活跃度计算
            userActivity.add(userData);
        }
        // 按活跃度排序取Top 10
        userActivity.sort((a, b) -> ((Long) b.get("activityScore")).compareTo((Long) a.get("activityScore")));
        result.put("userActivity", userActivity.stream().limit(10).collect(Collectors.toList()));

        // 4. 话题互动指标统计
        Map<String, Object> interactionStats = new HashMap<>();
        // 平均阅读量
        List<Topic> allTopics = _TopicMpper.selectList(
                Wrappers.<Topic>lambdaQuery()
                        .eq(Topic::getAuditStatus, AuditStatusEnum.审核通过.index()));
        double avgViewCount = allTopics.stream()
                .mapToInt(t -> t.getViewCount() != null ? t.getViewCount() : 0)
                .average()
                .orElse(0.0);
        // 平均评论数
        double avgCommentCount = (double) _CommentMapper.selectCount(null)
                / (allTopics.size() > 0 ? allTopics.size() : 1);
        // 平均收藏数
        double avgCollectCount = (double) _TopicCollectMapper.selectCount(null)
                / (allTopics.size() > 0 ? allTopics.size() : 1);

        interactionStats.put("avgViewCount", Math.round(avgViewCount * 100.0) / 100.0);
        interactionStats.put("avgCommentCount", Math.round(avgCommentCount * 100.0) / 100.0);
        interactionStats.put("avgCollectCount", Math.round(avgCollectCount * 100.0) / 100.0);
        result.put("interactionStats", interactionStats);

        // 5. 话题质量分布
        Map<String, Object> qualityDistribution = new HashMap<>();
        long highQualityCount = allTopics.stream()
                .filter(t -> (t.getViewCount() != null && t.getViewCount() > avgViewCount))
                .count();
        long mediumQualityCount = allTopics.stream()
                .filter(t -> (t.getViewCount() != null && t.getViewCount() <= avgViewCount
                        && t.getViewCount() > avgViewCount / 2))
                .count();
        long lowQualityCount = allTopics.stream()
                .filter(t -> (t.getViewCount() == null || t.getViewCount() <= avgViewCount / 2))
                .count();

        qualityDistribution.put("high", highQualityCount);
        qualityDistribution.put("medium", mediumQualityCount);
        qualityDistribution.put("low", lowQualityCount);
        result.put("qualityDistribution", qualityDistribution);

        return result;
    }

    /**
     * 根据话题ID推荐相似内容的话题
     *
     * @param input 话题ID
     * @return 相似话题的分页结果
     */
    @SneakyThrows
    @Override
    public PagedResult<TopicDto> ContentRecommendTop10(TopicPagedInput input) {
        Integer topicId = input.getId();
        if (topicId == null) {
            return PagedResult.GetEmptyInstance();
        }

        // 1. 获取目标话题
        Topic targetTopic = _TopicMpper.selectById(topicId);
        if (targetTopic == null || targetTopic.getAuditStatus() != AuditStatusEnum.审核通过.index()) {
            return PagedResult.GetEmptyInstance();
        }

        // 2. 获取所有审核通过的话题（排除目标话题自身）
        List<Topic> allTopics = _TopicMpper.selectList(
                Wrappers.<Topic>lambdaQuery()
                        .eq(Topic::getAuditStatus, AuditStatusEnum.审核通过.index())
                        .ne(Topic::getId, topicId));

        if (allTopics.isEmpty()) {
            return PagedResult.GetEmptyInstance();
        }

        // 3. 提取目标话题的特征向量
        Map<String, Double> targetVector = extractFeatureVector(targetTopic);

        // 4. 计算所有话题与目标话题的相似度
        Map<Integer, Double> similarityScores = new HashMap<>();
        Set<String> allKeywords = new HashSet<>(targetVector.keySet());

        for (Topic topic : allTopics) {
            Map<String, Double> topicVector = extractFeatureVector(topic);
            // 合并关键词集合
            allKeywords.addAll(topicVector.keySet());

            // 计算余弦相似度
            double similarity = calculateCosineSimilarity(targetVector, topicVector, allKeywords);
            similarityScores.put(topic.getId(), similarity);
        }

        // 5. 根据相似度排序话题
        List<Integer> recommendedTopicIds = similarityScores.entrySet().stream()
                .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .limit(10) // 限制为前10个最相似的话题
                .collect(Collectors.toList());

        if (recommendedTopicIds.isEmpty()) {
            return PagedResult.GetEmptyInstance();
        }

        // 6. 查询推荐话题详情
        List<Topic> recommendedTopics = _TopicMpper.selectList(
                Wrappers.<Topic>lambdaQuery()
                        .in(Topic::getId, recommendedTopicIds)
                        .eq(Topic::getAuditStatus, AuditStatusEnum.审核通过.index()));

        // 按照相似度排序
        Map<Integer, Integer> topicIdToIndex = new HashMap<>();
        for (int i = 0; i < recommendedTopicIds.size(); i++) {
            topicIdToIndex.put(recommendedTopicIds.get(i), i);
        }

        recommendedTopics.sort((a, b) -> {
            return topicIdToIndex.get(a.getId()) - topicIdToIndex.get(b.getId());
        });

        // 7. 转换为DTO并处理
        List<TopicDto> items = Extension.copyBeanList(recommendedTopics, TopicDto.class);
        for (TopicDto item : items) {
            ProcessTopicDto(item);
        }

        return Extension.PagedResultBuild(items, input);
    }
}
