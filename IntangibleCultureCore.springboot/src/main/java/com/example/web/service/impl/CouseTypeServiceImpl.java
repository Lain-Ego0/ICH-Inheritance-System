package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.CouseTypeDto;
import com.example.web.dto.query.CouseTypePagedInput;
import com.example.web.entity.CouseType;
import com.example.web.mapper.AppUserMapper;
import com.example.web.mapper.CouseTypeMapper;
import com.example.web.service.CouseTypeService;
import com.example.web.tools.Extension;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 课程分类功能实现类
 */
@Service
public class CouseTypeServiceImpl extends ServiceImpl<CouseTypeMapper, CouseType> implements CouseTypeService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的CouseType表mapper对象
     */
    @Autowired
    private CouseTypeMapper CouseTypeMapper;

    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<CouseType> BuilderQuery(CouseTypePagedInput input) {
        // 声明一个支持课程分类查询的(拉姆达)表达式
        LambdaQueryWrapper<CouseType> queryWrapper = Wrappers.<CouseType>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, CouseType::getId, input.getId())
                .eq(input.getCreatorId() != null, CouseType::getCreatorId, input.getCreatorId());
        // 如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getName())) {
            queryWrapper = queryWrapper.like(CouseType::getName, input.getName());
        }
        return queryWrapper;
    }

    /**
     * 处理课程分类对于的外键数据
     */
    private List<CouseTypeDto> DispatchItem(List<CouseTypeDto> items)
            throws InvocationTargetException, IllegalAccessException {

        for (CouseTypeDto item : items) {
        }
        return items;
    }

    /**
     * 课程分类分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<CouseTypeDto> List(CouseTypePagedInput input) {
        // 构建where条件+排序
        LambdaQueryWrapper<CouseType> queryWrapper = BuilderQuery(input);

        // 按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(CouseType::getCreationTime);
        // 构建一个分页查询的model
        Page<CouseType> page = new Page<>(input.getPage(), input.getLimit());
        // 从数据库进行分页查询获取课程分类数据
        IPage<CouseType> pageRecords = CouseTypeMapper.selectPage(page, queryWrapper);
        // 获取所有满足条件的数据行数
        Long totalCount = CouseTypeMapper.selectCount(queryWrapper);
        // 把CouseType实体转换成CouseType传输模型
        List<CouseTypeDto> items = Extension.copyBeanList(pageRecords.getRecords(), CouseTypeDto.class);
        // 计算表的数据
        items = DispatchItem(items);

        // 返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个课程分类查询
     */
    @SneakyThrows
    @Override
    public CouseTypeDto Get(CouseTypePagedInput input) {
        if (input.getId() == null) {
            return new CouseTypeDto();
        }
        PagedResult<CouseTypeDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new CouseTypeDto());
    }

    /**
     * 课程分类创建或者修改
     */
    @SneakyThrows
    @Override
    public CouseTypeDto CreateOrEdit(CouseTypeDto input) {
        // 声明一个课程分类实体
        CouseType CouseType = input.MapToEntity();
        // 调用数据库的增加或者修改方法
        saveOrUpdate(CouseType);
        // 把传输模型返回给前端
        return CouseType.MapToDto();
    }

    /**
     * 课程分类删除
     */
    @Override
    public void Delete(IdInput input) {
        CouseType entity = CouseTypeMapper.selectById(input.getId());
        CouseTypeMapper.deleteById(entity);
    }

    /**
     * 课程分类批量删除
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
