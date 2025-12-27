package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.GoodPropDto;
import com.example.web.dto.query.GoodPropPagedInput;
import com.example.web.entity.AppUser;
import com.example.web.entity.Good;
import com.example.web.entity.GoodProp;
import com.example.web.mapper.AppUserMapper;
import com.example.web.mapper.GoodMapper;
import com.example.web.mapper.GoodPropMapper;
import com.example.web.service.GoodPropService;
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
 * 文创品属性功能实现类
 */
@Service
public class GoodPropServiceImpl extends ServiceImpl<GoodPropMapper, GoodProp> implements GoodPropService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的GoodProp表mapper对象
     */
    @Autowired
    private GoodPropMapper GoodPropMapper;
    @Autowired
    private GoodMapper GoodMapper;

    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<GoodProp> BuilderQuery(GoodPropPagedInput input) {
        // 声明一个支持文创品属性查询的(拉姆达)表达式
        LambdaQueryWrapper<GoodProp> queryWrapper = Wrappers.<GoodProp>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, GoodProp::getId, input.getId())
                .eq(input.getCreatorId() != null, GoodProp::getCreatorId, input.getCreatorId());
        // 如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getNameLike())) {
            queryWrapper = queryWrapper.like(GoodProp::getName, input.getNameLike());
        }
        if (Extension.isNotNullOrEmpty(input.getValueLike())) {
            queryWrapper = queryWrapper.like(GoodProp::getValue, input.getValueLike());
        }

        if (input.getGoodId() != null) {
            queryWrapper = queryWrapper.eq(GoodProp::getGoodId, input.getGoodId());
        }
        return queryWrapper;
    }

    /**
     * 处理文创品属性对于的外键数据
     */
    private List<GoodPropDto> DispatchItem(List<GoodPropDto> items)
            throws InvocationTargetException, IllegalAccessException {

        for (GoodPropDto item : items) {
            // 查询出关联的创建用户信息

            AppUser CreatorAppUserEntity = AppUserMapper
                    .selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getCreatorId())).stream()
                    .findFirst().orElse(new AppUser());
            item.setCreatorAppUserDto(CreatorAppUserEntity.MapToDto());

            // 查询出关联的Good表信息
            Good GoodEntity = GoodMapper.selectList(Wrappers.<Good>lambdaQuery().eq(Good::getId, item.getGoodId()))
                    .stream().findFirst().orElse(new Good());
            item.setGoodDto(GoodEntity.MapToDto());
        }
        return items;
    }

    /**
     * 文创品属性分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<GoodPropDto> List(GoodPropPagedInput input) {
        // 构建where条件+排序
        LambdaQueryWrapper<GoodProp> queryWrapper = BuilderQuery(input);

        // 按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(GoodProp::getCreationTime);
        // 构建一个分页查询的model
        Page<GoodProp> page = new Page<>(input.getPage(), input.getLimit());
        // 从数据库进行分页查询获取文创品属性数据
        IPage<GoodProp> pageRecords = GoodPropMapper.selectPage(page, queryWrapper);
        // 获取所有满足条件的数据行数
        Long totalCount = GoodPropMapper.selectCount(queryWrapper);
        // 把GoodProp实体转换成GoodProp传输模型
        List<GoodPropDto> items = Extension.copyBeanList(pageRecords.getRecords(), GoodPropDto.class);
        // 计算表的数据
        items = DispatchItem(items);

        // 返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个文创品属性查询
     */
    @SneakyThrows
    @Override
    public GoodPropDto Get(GoodPropPagedInput input) {
        if (input.getId() == null) {
            return new GoodPropDto();
        }
        PagedResult<GoodPropDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new GoodPropDto());
    }

    /**
     * 文创品属性创建或者修改
     */
    @SneakyThrows
    @Override
    public GoodPropDto CreateOrEdit(GoodPropDto input) {
        // 声明一个文创品属性实体
        GoodProp GoodProp = input.MapToEntity();
        // 调用数据库的增加或者修改方法
        saveOrUpdate(GoodProp);
        // 把传输模型返回给前端
        return GoodProp.MapToDto();
    }

    /**
     * 文创品属性删除
     */
    @Override
    public void Delete(IdInput input) {
        GoodProp entity = GoodPropMapper.selectById(input.getId());
        GoodPropMapper.deleteById(entity);
    }

    /**
     * 文创品属性批量删除
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
