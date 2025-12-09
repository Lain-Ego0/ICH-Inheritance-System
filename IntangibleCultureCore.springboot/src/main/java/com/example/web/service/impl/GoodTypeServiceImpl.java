package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.GoodTypeDto;
import com.example.web.dto.query.GoodTypePagedInput;
import com.example.web.entity.AppUser;
import com.example.web.entity.GoodType;
import com.example.web.mapper.AppUserMapper;
import com.example.web.mapper.GoodTypeMapper;
import com.example.web.mapper.ShopMapper;
import com.example.web.service.GoodTypeService;
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
 * 文创品分类功能实现类
 */
@Service
public class GoodTypeServiceImpl extends ServiceImpl<GoodTypeMapper, GoodType> implements GoodTypeService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的GoodType表mapper对象
     */
    @Autowired
    private GoodTypeMapper GoodTypeMapper;

    @Autowired
    private ShopMapper ShopMapper;

    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<GoodType> BuilderQuery(GoodTypePagedInput input) {
        // 声明一个支持文创品分类查询的(拉姆达)表达式
        LambdaQueryWrapper<GoodType> queryWrapper = Wrappers.<GoodType>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, GoodType::getId, input.getId())
                .eq(input.getCreatorId() != null, GoodType::getCreatorId, input.getCreatorId());
        // 如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getNameLike())) {
            queryWrapper = queryWrapper.like(GoodType::getName, input.getNameLike());
        }

        return queryWrapper;
    }

    /**
     * 处理文创品分类对于的外键数据
     */
    private List<GoodTypeDto> DispatchItem(List<GoodTypeDto> items)
            throws InvocationTargetException, IllegalAccessException {

        for (GoodTypeDto item : items) {
            // 查询出关联的创建用户信息

            AppUser CreatorAppUserEntity = AppUserMapper
                    .selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getCreatorId())).stream()
                    .findFirst().orElse(new AppUser());
            item.setCreatorAppUserDto(CreatorAppUserEntity.MapToDto());

        }
        return items;
    }

    /**
     * 文创品分类分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<GoodTypeDto> List(GoodTypePagedInput input) {
        // 构建where条件+排序
        LambdaQueryWrapper<GoodType> queryWrapper = BuilderQuery(input);

        // 按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(GoodType::getSort);
        // 构建一个分页查询的model
        Page<GoodType> page = new Page<>(input.getPage(), input.getLimit());
        // 从数据库进行分页查询获取文创品分类数据
        IPage<GoodType> pageRecords = GoodTypeMapper.selectPage(page, queryWrapper);
        // 获取所有满足条件的数据行数
        Long totalCount = GoodTypeMapper.selectCount(queryWrapper);
        // 把GoodType实体转换成GoodType传输模型
        List<GoodTypeDto> items = Extension.copyBeanList(pageRecords.getRecords(), GoodTypeDto.class);
        // 计算表的数据
        items = DispatchItem(items);

        // 返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个文创品分类查询
     */
    @SneakyThrows
    @Override
    public GoodTypeDto Get(GoodTypePagedInput input) {
        if (input.getId() == null) {
            return new GoodTypeDto();
        }
        PagedResult<GoodTypeDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new GoodTypeDto());
    }

    /**
     * 文创品分类创建或者修改
     */
    @SneakyThrows
    @Override
    public GoodTypeDto CreateOrEdit(GoodTypeDto input) {
        // 声明一个文创品分类实体
        GoodType GoodType = input.MapToEntity();
        // 调用数据库的增加或者修改方法
        saveOrUpdate(GoodType);
        // 把传输模型返回给前端
        return GoodType.MapToDto();
    }

    /**
     * 文创品分类删除
     */
    @Override
    public void Delete(IdInput input) {
        GoodType entity = GoodTypeMapper.selectById(input.getId());
        GoodTypeMapper.deleteById(entity);
    }

    /**
     * 文创品分类批量删除
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
