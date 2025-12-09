package com.example.web.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.VideoExhibitionDto;
import com.example.web.dto.VideoExhibitionTypeDto;
import com.example.web.dto.query.VideoExhibitionPagedInput;
import com.example.web.entity.VideoExhibition;
import com.example.web.entity.VideoExhibitionType;
import com.example.web.mapper.AppUserMapper;
import com.example.web.mapper.VideoExhibitionMapper;
import com.example.web.mapper.VideoExhibitionTypeMapper;
import com.example.web.service.VideoExhibitionService;
import com.example.web.tools.Extension;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;

import lombok.SneakyThrows;

/**
 * 视频展览功能实现类
 */
@Service
public class VideoExhibitionServiceImpl extends ServiceImpl<VideoExhibitionMapper, VideoExhibition>
        implements VideoExhibitionService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的VideoExhibition表mapper对象
     */
    @Autowired
    private VideoExhibitionMapper VideoExhibitionMapper;
    @Autowired
    private VideoExhibitionTypeMapper VideoExhibitionTypeMapper;

    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<VideoExhibition> BuilderQuery(VideoExhibitionPagedInput input) {
        // 声明一个支持视频展览查询的(拉姆达)表达式
        LambdaQueryWrapper<VideoExhibition> queryWrapper = Wrappers.<VideoExhibition>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, VideoExhibition::getId, input.getId())
                .eq(input.getCreatorId() != null, VideoExhibition::getCreatorId, input.getCreatorId());
        // 如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getShortDesc())) {
            queryWrapper = queryWrapper.like(VideoExhibition::getShortDesc, input.getShortDesc());
        }
        if (Extension.isNotNullOrEmpty(input.getProviceCityArea())) {
            queryWrapper = queryWrapper.like(VideoExhibition::getProviceCityArea, input.getProviceCityArea());
        }
        if (input.getIsPutaway() != null) {
            queryWrapper = queryWrapper.eq(VideoExhibition::getIsPutaway, input.getIsPutaway());
        }
        if (input.getVideoExhibitionTypeId() != null) {
            queryWrapper = queryWrapper.eq(VideoExhibition::getVideoExhibitionTypeId, input.getVideoExhibitionTypeId());
        }
        return queryWrapper;
    }

    /**
     * 处理视频展览对于的外键数据
     */
    private List<VideoExhibitionDto> DispatchItem(List<VideoExhibitionDto> items)
            throws InvocationTargetException, IllegalAccessException {

        for (VideoExhibitionDto item : items) {

            // 查询出关联的VideoExhibitionType表信息
            VideoExhibitionType VideoExhibitionTypeEntity = VideoExhibitionTypeMapper
                    .selectById(item.getVideoExhibitionTypeId());
            item.setVideoExhibitionTypeDto(VideoExhibitionTypeEntity != null ? VideoExhibitionTypeEntity.MapToDto()
                    : new VideoExhibitionTypeDto());
        }

        return items;
    }

    /**
     * 视频展览分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<VideoExhibitionDto> List(VideoExhibitionPagedInput input) {
        // 构建where条件+排序
        LambdaQueryWrapper<VideoExhibition> queryWrapper = BuilderQuery(input);

        // 按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(VideoExhibition::getCreationTime);
        // 构建一个分页查询的model
        Page<VideoExhibition> page = new Page<>(input.getPage(), input.getLimit());
        // 从数据库进行分页查询获取视频展览数据
        IPage<VideoExhibition> pageRecords = VideoExhibitionMapper.selectPage(page, queryWrapper);
        // 获取所有满足条件的数据行数
        Long totalCount = VideoExhibitionMapper.selectCount(queryWrapper);
        // 把VideoExhibition实体转换成VideoExhibition传输模型
        List<VideoExhibitionDto> items = Extension.copyBeanList(pageRecords.getRecords(), VideoExhibitionDto.class);

        DispatchItem(items);
        // 返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个视频展览查询
     */
    @SneakyThrows
    @Override
    public VideoExhibitionDto Get(VideoExhibitionPagedInput input) {
        if (input.getId() == null) {
            return new VideoExhibitionDto();
        }

        PagedResult<VideoExhibitionDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new VideoExhibitionDto());
    }

    /**
     * 视频展览创建或者修改
     */
    @SneakyThrows
    @Override
    public VideoExhibitionDto CreateOrEdit(VideoExhibitionDto input) {
        // 声明一个视频展览实体
        VideoExhibition VideoExhibition = input.MapToEntity();
        // 调用数据库的增加或者修改方法
        saveOrUpdate(VideoExhibition);
        // 把传输模型返回给前端
        return VideoExhibition.MapToDto();
    }

    /**
     * 视频展览删除
     */
    @Override
    public void Delete(IdInput input) {
        VideoExhibition entity = VideoExhibitionMapper.selectById(input.getId());
        VideoExhibitionMapper.deleteById(entity);
    }

    /**
     * 视频展览批量删除
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
     * 基于内容推荐
     */
    @SneakyThrows
    @Override
    public PagedResult<VideoExhibitionDto> RecommendByContent(VideoExhibitionPagedInput input) {
        // 查询目标视频展览
        VideoExhibition videoExhibitionEntity = VideoExhibitionMapper.selectById(input.getId());
        if (videoExhibitionEntity == null) {
            return PagedResult.GetEmptyInstance();
        }
        // 得到类别
        VideoExhibitionType videoExhibitionTypeEntity = VideoExhibitionTypeMapper
                .selectById(videoExhibitionEntity.getVideoExhibitionTypeId());
        if (videoExhibitionTypeEntity == null) {
            return PagedResult.GetEmptyInstance();
        }
        // 获取目标视频展览的描述和内容
        String targetShortDesc = videoExhibitionEntity.getShortDesc();
        String targetTypeName = videoExhibitionTypeEntity.getName();

        // 如果描述或内容为空，则无法进行内容推荐
        if (Extension.isNullOrEmpty(targetShortDesc) && Extension.isNullOrEmpty(targetTypeName)) {
            return PagedResult.GetEmptyInstance();
        }

        // 查询所有视频展览项目（排除当前项目）
        LambdaQueryWrapper<VideoExhibition> queryWrapper = Wrappers.<VideoExhibition>lambdaQuery()
                .ne(VideoExhibition::getId, input.getId());

        // 获取所有候选项目
        List<VideoExhibition> allVideoExhibitions = VideoExhibitionMapper.selectList(queryWrapper);

        // 使用Map存储VideoExhibition对象及其相似度
        java.util.Map<VideoExhibition, Double> videoExhibitionSimilarityMap = new java.util.HashMap<>();

        // 计算每个VideoExhibition对象的相似度并存入Map
        for (VideoExhibition videoExhibition : allVideoExhibitions) {
            if (videoExhibition != null) {
                VideoExhibitionType typeEntity = VideoExhibitionTypeMapper
                        .selectById(videoExhibition.getVideoExhibitionTypeId());
                if (typeEntity == null) {
                    continue;
                }
                String videoExhibitionTypeName = videoExhibitionTypeEntity.getName();

                double descSimilarity = calculateSimilarity(targetShortDesc, videoExhibition.getShortDesc());
                double typeNameSimilarity = calculateSimilarity(targetTypeName, videoExhibitionTypeName);

                // 综合相似度（描述权重0.4，内容权重0.6）
                double similarity = descSimilarity * 0.4 + typeNameSimilarity * 0.6;

                videoExhibitionSimilarityMap.put(videoExhibition, similarity);
            }
        }

        // 过滤掉相似度小于0.2的
        videoExhibitionSimilarityMap.entrySet().removeIf(entry -> entry.getValue() < 0.2);

        // 按相似度降序排序并限制数量
        List<VideoExhibition> recommendedVideoExhibitions = videoExhibitionSimilarityMap.entrySet().stream()
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                .limit(input.getLimit())
                .map(java.util.Map.Entry::getKey)
                .collect(java.util.stream.Collectors.toList());

        // 转换为DTO
        List<VideoExhibitionDto> items = Extension.copyBeanList(recommendedVideoExhibitions, VideoExhibitionDto.class);

        // 处理外键关联数据
        DispatchItem(items);

        // 返回分页结果
        return PagedResult.GetInstance(items, (long) recommendedVideoExhibitions.size());
    }

    /**
     * 计算两个文本之间的相似度
     * 使用TF-IDF和余弦相似度算法
     * 
     * @param text1 文本1
     * @param text2 文本2
     * @return 相似度（0-1之间的值）
     */
    private double calculateSimilarity(String text1, String text2) {
        // 如果任一文本为空，则相似度为0
        if (Extension.isNullOrEmpty(text1) || Extension.isNullOrEmpty(text2)) {
            return 0.0;
        }

        try {
            // 使用分词进行中文分词
            List<String> words1 = segmentText(text1);
            List<String> words2 = segmentText(text2);

            // 构建词频向量
            Map<String, Integer> vector1 = buildTermFrequencyVector(words1);
            Map<String, Integer> vector2 = buildTermFrequencyVector(words2);

            // 计算余弦相似度
            return calculateCosineSimilarity(vector1, vector2);
        } catch (Exception e) {
            // 如果分词过程出错，回退到简单的Jaccard相似度
            return calculateJaccardSimilarity(text1, text2);
        }
    }

    /**
     * 进行中文分词
     * 
     * @param text 待分词文本
     * @return 分词结果列表
     */
    private List<String> segmentText(String text) {
        // 简化实现，按照中文字符、英文单词和数字进行分割
        List<String> result = new ArrayList<>();
        String regex = "[\\p{L}\\p{N}]+"; // 匹配连续的字母和数字
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String word = matcher.group().toLowerCase();
            if (word.length() > 1) { // 过滤掉单个字符
                result.add(word);
            }
        }

        return result;
    }

    /**
     * 构建词频向量
     * 
     * @param words 分词结果
     * @return 词频向量
     */
    private Map<String, Integer> buildTermFrequencyVector(List<String> words) {
        Map<String, Integer> vector = new HashMap<>();
        for (String word : words) {
            vector.put(word, vector.getOrDefault(word, 0) + 1);
        }
        return vector;
    }

    /**
     * 计算余弦相似度
     * 
     * @param vector1 词频向量1
     * @param vector2 词频向量2
     * @return 余弦相似度
     */
    private double calculateCosineSimilarity(Map<String, Integer> vector1, Map<String, Integer> vector2) {
        // 获取所有唯一词汇
        Set<String> allTerms = new HashSet<>(vector1.keySet());
        allTerms.addAll(vector2.keySet());

        double dotProduct = 0.0;
        double magnitude1 = 0.0;
        double magnitude2 = 0.0;

        // 计算点积和向量大小
        for (String term : allTerms) {
            int freq1 = vector1.getOrDefault(term, 0);
            int freq2 = vector2.getOrDefault(term, 0);

            dotProduct += freq1 * freq2;
            magnitude1 += Math.pow(freq1, 2);
            magnitude2 += Math.pow(freq2, 2);
        }

        // 计算余弦相似度
        magnitude1 = Math.sqrt(magnitude1);
        magnitude2 = Math.sqrt(magnitude2);

        if (magnitude1 == 0.0 || magnitude2 == 0.0) {
            return 0.0;
        }

        return dotProduct / (magnitude1 * magnitude2);
    }

    /**
     * 计算Jaccard相似度（作为备选方法）
     * 
     * @param text1 文本1
     * @param text2 文本2
     * @return Jaccard相似度
     */
    private double calculateJaccardSimilarity(String text1, String text2) {
        // 简单分词
        Set<String> words1 = tokenize(text1);
        Set<String> words2 = tokenize(text2);

        // 计算Jaccard相似度: 交集大小 / 并集大小
        Set<String> intersection = new HashSet<>(words1);
        intersection.retainAll(words2);

        Set<String> union = new HashSet<>(words1);
        union.addAll(words2);

        // 避免除以零
        if (union.isEmpty()) {
            return 0.0;
        }

        return (double) intersection.size() / union.size();
    }

    /**
     * 简单分词函数（作为备选方法）
     * 
     * @param text 待分词文本
     * @return 分词结果集合
     */
    private Set<String> tokenize(String text) {
        if (Extension.isNullOrEmpty(text)) {
            return new HashSet<>();
        }

        // 转为小写并按非字母数字字符分割
        String[] words = text.toLowerCase()
                .replaceAll("[^\\p{L}\\p{N}]", " ")
                .split("\\s+");

        // 过滤空字符串并转为Set
        return Arrays.stream(words)
                .filter(w -> !w.isEmpty())
                .collect(java.util.stream.Collectors.toSet());
    }

}
