package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.GoodStockDto;
import com.example.web.dto.query.GoodStockPagedInput;
import com.example.web.entity.AppUser;
import com.example.web.entity.Good;
import com.example.web.entity.GoodStock;
import com.example.web.mapper.AppUserMapper;
import com.example.web.mapper.GoodMapper;
import com.example.web.mapper.GoodStockMapper;
import com.example.web.service.GoodStockService;
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
 * 库存记录功能实现类
 */
@Service
public class GoodStockServiceImpl extends ServiceImpl<GoodStockMapper, GoodStock> implements GoodStockService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的GoodStock表mapper对象
     */
    @Autowired
    private GoodStockMapper GoodStockMapper;
    @Autowired
    private GoodMapper GoodMapper;

    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<GoodStock> BuilderQuery(GoodStockPagedInput input) {
        // 声明一个支持库存记录查询的(拉姆达)表达式
        LambdaQueryWrapper<GoodStock> queryWrapper = Wrappers.<GoodStock>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, GoodStock::getId, input.getId())
                .eq(input.getCreatorId() != null, GoodStock::getCreatorId, input.getCreatorId());
        // 如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getRemark())) {
            queryWrapper = queryWrapper.like(GoodStock::getRemark, input.getRemark());
        }

        if (input.getGoodId() != null) {
            queryWrapper = queryWrapper.eq(GoodStock::getGoodId, input.getGoodId());
        }

        return queryWrapper;
    }

    /**
     * 处理库存记录对于的外键数据
     */
    private List<GoodStockDto> DispatchItem(List<GoodStockDto> items)
            throws InvocationTargetException, IllegalAccessException {

        for (GoodStockDto item : items) {
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
     * 库存记录分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<GoodStockDto> List(GoodStockPagedInput input) {
        // 构建where条件+排序
        LambdaQueryWrapper<GoodStock> queryWrapper = BuilderQuery(input);

        // 按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(GoodStock::getCreationTime);
        // 构建一个分页查询的model
        Page<GoodStock> page = new Page<>(input.getPage(), input.getLimit());
        // 从数据库进行分页查询获取库存记录数据
        IPage<GoodStock> pageRecords = GoodStockMapper.selectPage(page, queryWrapper);
        // 获取所有满足条件的数据行数
        Long totalCount = GoodStockMapper.selectCount(queryWrapper);
        // 把GoodStock实体转换成GoodStock传输模型
        List<GoodStockDto> items = Extension.copyBeanList(pageRecords.getRecords(), GoodStockDto.class);
        // 计算表的数据
        items = DispatchItem(items);

        // 返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个库存记录查询
     */
    @SneakyThrows
    @Override
    public GoodStockDto Get(GoodStockPagedInput input) {
        if (input.getId() == null) {
            return new GoodStockDto();
        }
        PagedResult<GoodStockDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new GoodStockDto());
    }

    /**
     * 库存记录创建或者修改
     */
    @SneakyThrows
    @Override
    public GoodStockDto CreateOrEdit(GoodStockDto input) {

        Good good = GoodMapper.selectById(input.getGoodId());

        good.setStock(good.getStock() + input.getQty());

        GoodMapper.updateById(good);
        // 声明一个库存记录实体
        GoodStock GoodStock = input.MapToEntity();
        // 调用数据库的增加或者修改方法
        saveOrUpdate(GoodStock);
        // 把传输模型返回给前端
        return GoodStock.MapToDto();
    }

    /**
     * 库存记录删除
     */
    @Override
    public void Delete(IdInput input) {
        GoodStock entity = GoodStockMapper.selectById(input.getId());
        GoodStockMapper.deleteById(entity);
    }

    /**
     * 库存记录批量删除
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
