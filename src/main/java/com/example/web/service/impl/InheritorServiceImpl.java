package com.example.web.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.InheritorDto;
import com.example.web.dto.query.InheritorPagedInput;
import com.example.web.entity.Inheritor;
import com.example.web.mapper.AppUserMapper;
import com.example.web.mapper.InheritorMapper;
import com.example.web.service.InheritorService;
import com.example.web.tools.Extension;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;

import lombok.SneakyThrows;

/**
 * 传人功能实现类
 */
@Service
public class InheritorServiceImpl extends ServiceImpl<InheritorMapper, Inheritor> implements InheritorService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的Inheritor表mapper对象
     */
    @Autowired
    private InheritorMapper InheritorMapper;

    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<Inheritor> BuilderQuery(InheritorPagedInput input) {
        // 声明一个支持传人查询的(拉姆达)表达式
        LambdaQueryWrapper<Inheritor> queryWrapper = Wrappers.<Inheritor>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, Inheritor::getId, input.getId())
                .eq(input.getCreatorId() != null, Inheritor::getCreatorId, input.getCreatorId());
        // 如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getName())) {
            queryWrapper = queryWrapper.like(Inheritor::getName, input.getName());
        }
        if (Extension.isNotNullOrEmpty(input.getSex())) {
            queryWrapper = queryWrapper.like(Inheritor::getSex, input.getSex());
        }
        if (Extension.isNotNullOrEmpty(input.getNation())) {
            queryWrapper = queryWrapper.like(Inheritor::getNation, input.getNation());
        }
        if (Extension.isNotNullOrEmpty(input.getNo())) {
            queryWrapper = queryWrapper.like(Inheritor::getNo, input.getNo());
        }
        if (Extension.isNotNullOrEmpty(input.getProviceCityArea())) {
            queryWrapper = queryWrapper.like(Inheritor::getProviceCityArea, input.getProviceCityArea());
        }
        if (input.getBirthDateRange() != null && !input.getBirthDateRange().isEmpty()) {
            queryWrapper = queryWrapper.le(Inheritor::getBirthDate, input.getBirthDateRange().get(1));
            queryWrapper = queryWrapper.ge(Inheritor::getBirthDate, input.getBirthDateRange().get(0));
        }
        if (Extension.isNotNullOrEmpty(input.getContent())) {
            queryWrapper = queryWrapper.like(Inheritor::getContent, input.getContent());
        }
        if (Extension.isNotNullOrEmpty(input.getKeyWord())) {
            queryWrapper = queryWrapper.like(Inheritor::getName, input.getKeyWord());
        }
        return queryWrapper;
    }

    /**
     * 处理传人对于的外键数据
     */
    private List<InheritorDto> DispatchItem(List<InheritorDto> items)
            throws InvocationTargetException, IllegalAccessException {

        for (InheritorDto item : items) {
        }

        return items;
    }

    /**
     * 传人分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<InheritorDto> List(InheritorPagedInput input) {
        // 构建where条件+排序
        LambdaQueryWrapper<Inheritor> queryWrapper = BuilderQuery(input);

        // 按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(Inheritor::getCreationTime);
        // 构建一个分页查询的model
        Page<Inheritor> page = new Page<>(input.getPage(), input.getLimit());
        // 从数据库进行分页查询获取传人数据
        IPage<Inheritor> pageRecords = InheritorMapper.selectPage(page, queryWrapper);
        // 获取所有满足条件的数据行数
        Long totalCount = InheritorMapper.selectCount(queryWrapper);
        // 把Inheritor实体转换成Inheritor传输模型
        List<InheritorDto> items = Extension.copyBeanList(pageRecords.getRecords(), InheritorDto.class);

        DispatchItem(items);
        // 返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个传人查询
     */
    @SneakyThrows
    @Override
    public InheritorDto Get(InheritorPagedInput input) {
        if (input.getId() == null) {
            return new InheritorDto();
        }

        PagedResult<InheritorDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new InheritorDto());
    }

    /**
     * 传人创建或者修改
     */
    @SneakyThrows
    @Override
    public InheritorDto CreateOrEdit(InheritorDto input) {
        // 声明一个传人实体
        Inheritor Inheritor = input.MapToEntity();
        // 调用数据库的增加或者修改方法
        saveOrUpdate(Inheritor);
        // 把传输模型返回给前端
        return Inheritor.MapToDto();
    }

    /**
     * 传人删除
     */
    @Override
    public void Delete(IdInput input) {
        Inheritor entity = InheritorMapper.selectById(input.getId());
        InheritorMapper.deleteById(entity);
    }

    /**
     * 传人批量删除
     */
    @Override
    public void BatchDelete(IdsInput input) {
        for (Integer id : input.getIds()) {
            IdInput idInput = new IdInput();
            idInput.setId(id);
            Delete(idInput);
        }
    }
}