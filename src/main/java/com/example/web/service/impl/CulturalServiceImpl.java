package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.CulturalDto;
import com.example.web.dto.CulturalTypeDto;
import com.example.web.dto.InheritorDto;
import com.example.web.dto.query.CulturalPagedInput;
import com.example.web.entity.Cultural;
import com.example.web.entity.CulturalType;
import com.example.web.entity.Inheritor;
import com.example.web.mapper.*;
import com.example.web.service.CulturalService;
import com.example.web.tools.Extension;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 非遗文化功能实现类
 */
@Service
public class CulturalServiceImpl extends ServiceImpl<CulturalMapper, Cultural> implements CulturalService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的Cultural表mapper对象
     */
    @Autowired
    private CulturalMapper CulturalMapper;
    @Autowired
    private InheritorMapper InheritorMapper;
    @Autowired
    private CulturalTypeMapper CulturalTypeMapper;



    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<Cultural> BuilderQuery(CulturalPagedInput input) {
        // 声明一个支持非遗文化查询的(拉姆达)表达式
        LambdaQueryWrapper<Cultural> queryWrapper = Wrappers.<Cultural>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, Cultural::getId, input.getId())
                .eq(input.getCreatorId() != null, Cultural::getCreatorId, input.getCreatorId());
        // 如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getName())) {
            queryWrapper = queryWrapper.like(Cultural::getName, input.getName());
        }
        if (Extension.isNotNullOrEmpty(input.getNo())) {
            queryWrapper = queryWrapper.like(Cultural::getNo, input.getNo());
        }
        if (Extension.isNotNullOrEmpty(input.getPlaceDeclaration())) {
            queryWrapper = queryWrapper.like(Cultural::getPlaceDeclaration, input.getPlaceDeclaration());
        }
        if (Extension.isNotNullOrEmpty(input.getProtectionUnit())) {
            queryWrapper = queryWrapper.like(Cultural::getProtectionUnit, input.getProtectionUnit());
        }
        if (Extension.isNotNullOrEmpty(input.getProviceCityArea())) {
            queryWrapper = queryWrapper.like(Cultural::getProviceCityArea, input.getProviceCityArea());
        }
        if (Extension.isNotNullOrEmpty(input.getBatchNo())) {
            queryWrapper = queryWrapper.like(Cultural::getBatchNo, input.getBatchNo());
        }

        if (input.getCulturalTypeId() != null) {
            queryWrapper = queryWrapper.eq(Cultural::getCulturalTypeId, input.getCulturalTypeId());
        }

        if (input.getInheritorId() != null) {
            queryWrapper = queryWrapper.eq(Cultural::getInheritorId, input.getInheritorId());
        }
        if (input.getRecordDateRange() != null && !input.getRecordDateRange().isEmpty()) {
            queryWrapper = queryWrapper.le(Cultural::getRecordDate, input.getRecordDateRange().get(1));
            queryWrapper = queryWrapper.ge(Cultural::getRecordDate, input.getRecordDateRange().get(0));
        }
        if (Extension.isNotNullOrEmpty(input.getContent())) {
            queryWrapper = queryWrapper.like(Cultural::getContent, input.getContent());
        }
        if (Extension.isNotNullOrEmpty(input.getKeyWord())) {
            queryWrapper = queryWrapper.like(Cultural::getName, input.getKeyWord());
            queryWrapper = queryWrapper.or().like(Cultural::getNo, input.getKeyWord());
            queryWrapper = queryWrapper.or().like(Cultural::getPlaceDeclaration, input.getKeyWord());
            queryWrapper = queryWrapper.or().like(Cultural::getProtectionUnit, input.getKeyWord());
            queryWrapper = queryWrapper.or().like(Cultural::getProviceCityArea, input.getKeyWord());
            queryWrapper = queryWrapper.or().like(Cultural::getBatchNo, input.getKeyWord());
            queryWrapper = queryWrapper.or().like(Cultural::getContent, input.getKeyWord());
        }
        return queryWrapper;
    }

    /**
     * 处理非遗文化对于的外键数据
     */
    private List<CulturalDto> DispatchItem(List<CulturalDto> items)
            throws InvocationTargetException, IllegalAccessException {

        for (CulturalDto item : items) {

            // 查询出关联的Inheritor表信息
            Inheritor InheritorEntity = InheritorMapper.selectById(item.getInheritorId());
            item.setInheritorDto(InheritorEntity != null ? InheritorEntity.MapToDto() : new InheritorDto());

            // 查询出关联的CulturalType表信息
            CulturalType CulturalTypeEntity = CulturalTypeMapper.selectById(item.getCulturalTypeId());
            item.setCulturalTypeDto(CulturalTypeEntity != null ? CulturalTypeEntity.MapToDto() : new CulturalTypeDto());
        }

        return items;
    }

    /**
     * 非遗文化分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<CulturalDto> List(CulturalPagedInput input) {
        // 构建where条件+排序
        LambdaQueryWrapper<Cultural> queryWrapper = BuilderQuery(input);

        // 按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(Cultural::getCreationTime);
        // 构建一个分页查询的model
        Page<Cultural> page = new Page<>(input.getPage(), input.getLimit());
        // 从数据库进行分页查询获取非遗文化数据
        IPage<Cultural> pageRecords = CulturalMapper.selectPage(page, queryWrapper);
        // 获取所有满足条件的数据行数
        Long totalCount = CulturalMapper.selectCount(queryWrapper);
        // 把Cultural实体转换成Cultural传输模型
        List<CulturalDto> items = Extension.copyBeanList(pageRecords.getRecords(), CulturalDto.class);

        DispatchItem(items);
        // 返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个非遗文化查询
     */
    @SneakyThrows
    @Override
    public CulturalDto Get(CulturalPagedInput input) {
        if (input.getId() == null) {
            return new CulturalDto();
        }

        PagedResult<CulturalDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new CulturalDto());
    }

    /**
     * 非遗文化创建或者修改
     */
    @SneakyThrows
    @Override
    public CulturalDto CreateOrEdit(CulturalDto input) {
        // 声明一个非遗文化实体
        Cultural Cultural = input.MapToEntity();
        // 调用数据库的增加或者修改方法
        saveOrUpdate(Cultural);
        // 把传输模型返回给前端
        return Cultural.MapToDto();
    }

    /**
     * 非遗文化删除
     */
    @Override
    public void Delete(IdInput input) {
        Cultural entity = CulturalMapper.selectById(input.getId());
        CulturalMapper.deleteById(entity);
    }

    /**
     * 非遗文化批量删除
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
     * 基于内容进行推荐
     * 
     * @param input
     * @return
     */
    @SneakyThrows
    @Override
    public PagedResult<CulturalDto> RecommendByContent(CulturalPagedInput input) {
        // 查询目标的
        Cultural CulturalEntity = CulturalMapper.selectById(input.getId());
        if (CulturalEntity == null) {
            return PagedResult.GetEmptyInstance();
        }

        // 获取目标文化项目的标题和内容
        String targetName = CulturalEntity.getName();
        String targetContent = CulturalEntity.getContent();

        // 如果标题或内容为空，则无法进行内容推荐
        if (Extension.isNullOrEmpty(targetName) && Extension.isNullOrEmpty(targetContent)) {
            return PagedResult.GetEmptyInstance();
        }

        // 查询所有非遗文化项目（排除当前项目）
        LambdaQueryWrapper<Cultural> queryWrapper = Wrappers.<Cultural>lambdaQuery()
                .ne(Cultural::getId, input.getId());

        // 获取所有候选项目
        List<Cultural> allCulturals = CulturalMapper.selectList(queryWrapper);

        // 使用Map存储Cultural对象及其相似度
        Map<Cultural, Double> culturalSimilarityMap = new HashMap<>();

        // 计算每个Cultural对象的相似度并存入Map
        for (Cultural cultural : allCulturals) {
            if (cultural != null) {
                double nameSimilarity = calculateSimilarity(targetName, cultural.getName());
                double contentSimilarity = calculateSimilarity(targetContent, cultural.getContent());

                // 综合相似度（标题权重0.4，内容权重0.6）
                double similarity = nameSimilarity * 0.4 + contentSimilarity * 0.6;

                culturalSimilarityMap.put(cultural, similarity);
            }
        }

        // 过滤掉相似度小于0.5的
        culturalSimilarityMap.entrySet().removeIf(entry -> entry.getValue() < 0.2);

        // 按相似度降序排序并限制数量
        List<Cultural> recommendedCulturals = culturalSimilarityMap.entrySet().stream()
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                .limit(input.getLimit())
                .map(Map.Entry::getKey)
                .collect(java.util.stream.Collectors.toList());

        // 转换为DTO
        List<CulturalDto> items = Extension.copyBeanList(recommendedCulturals, CulturalDto.class);

        // 处理外键关联数据
        DispatchItem(items);

        // 返回分页结果
        return PagedResult.GetInstance(items, (long) recommendedCulturals.size());
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
            // 使用HanLP进行中文分词
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
     * 使用HanLP进行中文分词
     * 
     * @param text 待分词文本
     * @return 分词结果列表
     */
    private List<String> segmentText(String text) {
        // 这里可以集成HanLP或其他中文分词库
        // 示例使用简化版，实际应用中应导入相应依赖
        // 例如：return
        // HanLP.segment(text).stream().map(Term::word).collect(Collectors.toList());

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

    /**
     * 综合分析
     * 
     * @return 包含多维度统计数据的HashMap
     */
    @Override
    @SneakyThrows
    public HashMap<String, Object> CulturalAnalyse() {
        HashMap<String, Object> result = new HashMap<>();

        // 获取所有非遗文化数据
        List<Cultural> allCulturals = CulturalMapper.selectList(null);

        // 1. 按类型统计非遗文化数量
        Map<Integer, Long> typeCountMap = new HashMap<>();
        Map<String, Long> typeNameCountMap = new HashMap<>();

        allCulturals.forEach(cultural -> {
            Integer typeId = cultural.getCulturalTypeId();
            typeCountMap.put(typeId, typeCountMap.getOrDefault(typeId, 0L) + 1);

            // 获取类型名称
            CulturalType type = CulturalTypeMapper.selectById(typeId);
            if (type != null) {
                String typeName = type.getName();
                typeNameCountMap.put(typeName, typeNameCountMap.getOrDefault(typeName, 0L) + 1);
            }
        });

        result.put("typeStatistics", typeNameCountMap);

        // 2. 按地区统计非遗文化数量（用于地图可视化）
        Map<String, Long> regionCountMap = new HashMap<>();

        allCulturals.forEach(cultural -> {
            String region = cultural.getProviceCityArea();
            if (Extension.isNotNullOrEmpty(region)) {
                // 提取省份信息（
                String province = region.split(",")[0];
                regionCountMap.put(province, regionCountMap.getOrDefault(province, 0L) + 1);
            }
        });

        result.put("regionStatistics", regionCountMap);

        // 3. 按批次号统计非遗文化数量
        Map<String, Long> batchCountMap = new HashMap<>();

        allCulturals.forEach(cultural -> {
            String batchNo = cultural.getBatchNo();
            if (Extension.isNotNullOrEmpty(batchNo)) {
                batchCountMap.put(batchNo, batchCountMap.getOrDefault(batchNo, 0L) + 1);
            }
        });

        result.put("batchStatistics", batchCountMap);

        // 4. 按时间统计非遗文化数量（按年份）
        Map<Integer, Long> yearCountMap = new HashMap<>();

        allCulturals.forEach(cultural -> {
            if (cultural.getRecordDate() != null) {
                // 提取年份
                int year = cultural.getRecordDate().getYear() + 1900; // Date.getYear()返回的是从1900年开始的年数
                yearCountMap.put(year, yearCountMap.getOrDefault(year, 0L) + 1);
            }
        });

        result.put("yearStatistics", yearCountMap);

        // 5. 按传承人统计非遗文化数量
        Map<String, Long> inheritorCountMap = new HashMap<>();

        allCulturals.forEach(cultural -> {
            Integer inheritorId = cultural.getInheritorId();
            if (inheritorId != null) {
                Inheritor inheritor = InheritorMapper.selectById(inheritorId);
                if (inheritor != null) {
                    String inheritorName = inheritor.getName();
                    inheritorCountMap.put(inheritorName, inheritorCountMap.getOrDefault(inheritorName, 0L) + 1);
                }
            }
        });

        result.put("inheritorStatistics", inheritorCountMap);

        // 6. 按保护单位统计
        Map<String, Long> protectionUnitCountMap = new HashMap<>();

        allCulturals.forEach(cultural -> {
            String protectionUnit = cultural.getProtectionUnit();
            if (Extension.isNotNullOrEmpty(protectionUnit)) {
                protectionUnitCountMap.put(protectionUnit, protectionUnitCountMap.getOrDefault(protectionUnit, 0L) + 1);
            }
        });

        result.put("protectionUnitStatistics", protectionUnitCountMap);

        // 7. 计算增长趋势（按年份的增长率）
        List<Map<String, Object>> growthTrend = new ArrayList<>();
        List<Integer> years = new ArrayList<>(yearCountMap.keySet());
        years.sort(Integer::compareTo);

        for (int i = 1; i < years.size(); i++) {
            int currentYear = years.get(i);
            int prevYear = years.get(i - 1);

            long currentCount = yearCountMap.get(currentYear);
            long prevCount = yearCountMap.get(prevYear);

            double growthRate = prevCount == 0 ? 0 : ((double) (currentCount - prevCount) / prevCount) * 100;

            Map<String, Object> yearGrowth = new HashMap<>();
            yearGrowth.put("year", currentYear);
            yearGrowth.put("count", currentCount);
            yearGrowth.put("growthRate", Math.round(growthRate * 100) / 100.0); // 保留两位小数

            growthTrend.add(yearGrowth);
        }

        result.put("growthTrend", growthTrend);

        // 8. 地区热力图数据（按地区的项目密度）
        Map<String, Object> heatMapData = new HashMap<>();
        heatMapData.put("regions", regionCountMap);

        // 计算最大值和最小值，用于前端热力图的颜色渐变
        long maxCount = regionCountMap.values().stream().mapToLong(Long::longValue).max().orElse(0);
        long minCount = regionCountMap.values().stream().mapToLong(Long::longValue).min().orElse(0);

        heatMapData.put("maxValue", maxCount);
        heatMapData.put("minValue", minCount);

        result.put("heatMapData", heatMapData);

        // 9. 总体统计信息
        Map<String, Object> overallStatistics = new HashMap<>();
        overallStatistics.put("totalCount", allCulturals.size());
        overallStatistics.put("typeCount", typeCountMap.size());
        overallStatistics.put("regionCount", regionCountMap.size());
        overallStatistics.put("inheritorCount", inheritorCountMap.size());

        result.put("overallStatistics", overallStatistics);

        // 10. 按申报地统计
        Map<String, Long> declarationPlaceCountMap = new HashMap<>();

        allCulturals.forEach(cultural -> {
            String place = cultural.getPlaceDeclaration();
            if (Extension.isNotNullOrEmpty(place)) {
                declarationPlaceCountMap.put(place, declarationPlaceCountMap.getOrDefault(place, 0L) + 1);
            }
        });

        result.put("declarationPlaceStatistics", declarationPlaceCountMap);

        return result;
    }
}
