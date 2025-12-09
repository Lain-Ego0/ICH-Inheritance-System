package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.AppUserDto;
import com.example.web.dto.CouseCollectDto;
import com.example.web.dto.CouseDto;
import com.example.web.dto.query.CouseCollectPagedInput;
import com.example.web.entity.AppUser;
import com.example.web.entity.Couse;
import com.example.web.entity.CouseCollect;
import com.example.web.mapper.AppUserMapper;
import com.example.web.mapper.CouseCollectMapper;
import com.example.web.mapper.CouseMapper;
import com.example.web.service.CouseCollectService;
import com.example.web.tools.Extension;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 课程收藏功能实现类
 */
@Service
public class CouseCollectServiceImpl extends ServiceImpl<CouseCollectMapper, CouseCollect>
        implements CouseCollectService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的CouseCollect表mapper对象
     */
    @Autowired
    private CouseCollectMapper CouseCollectMapper;
    @Autowired
    private CouseMapper CouseMapper;

    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<CouseCollect> BuilderQuery(CouseCollectPagedInput input) {
        // 声明一个支持课程收藏查询的(拉姆达)表达式
        LambdaQueryWrapper<CouseCollect> queryWrapper = Wrappers.<CouseCollect>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, CouseCollect::getId, input.getId())
                .eq(input.getCreatorId() != null, CouseCollect::getCreatorId, input.getCreatorId());
        // 如果前端搜索传入查询条件则拼接查询条件

        if (input.getCouseId() != null) {
            queryWrapper = queryWrapper.eq(CouseCollect::getCouseId, input.getCouseId());
        }

        if (input.getCollectUserId() != null) {
            queryWrapper = queryWrapper.eq(CouseCollect::getCollectUserId, input.getCollectUserId());
        }
        if (input.getTeachUserId() != null) {
            // 查询对应的课程
            List<Couse> couseList = CouseMapper
                    .selectList(Wrappers.<Couse>lambdaQuery().eq(Couse::getCreatorId, input.getTeachUserId()));
            if (couseList.size() > 0) {
                queryWrapper = queryWrapper.in(CouseCollect::getCouseId,
                        couseList.stream().map(Couse::getId).collect(Collectors.toList()));
            } else {
                queryWrapper = queryWrapper.eq(CouseCollect::getCouseId, 0);
            }
        }
        return queryWrapper;
    }

    /**
     * 处理课程收藏对于的外键数据
     */
    private List<CouseCollectDto> DispatchItem(List<CouseCollectDto> items)
            throws InvocationTargetException, IllegalAccessException {

        for (CouseCollectDto item : items) {
            // 查询创建人
            AppUser CreateUserEntity = AppUserMapper
                    .selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getCreatorId())).stream()
                    .findFirst().orElse(new AppUser());
            item.setCreatorAppUserDto(CreateUserEntity.MapToDto());
            // 查询出关联的AppUser表信息
            AppUser CollectUserEntity = AppUserMapper.selectById(item.getCollectUserId());
            item.setCollectUserDto(CollectUserEntity != null ? CollectUserEntity.MapToDto() : new AppUserDto());

            // 查询出关联的Couse表信息
            Couse CouseEntity = CouseMapper.selectById(item.getCouseId());
            item.setCouseDto(CouseEntity != null ? CouseEntity.MapToDto() : new CouseDto());
        }

        return items;
    }

    /**
     * 课程收藏分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<CouseCollectDto> List(CouseCollectPagedInput input) {
        // 构建where条件+排序
        LambdaQueryWrapper<CouseCollect> queryWrapper = BuilderQuery(input);

        // 按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(CouseCollect::getCreationTime);
        // 构建一个分页查询的model
        Page<CouseCollect> page = new Page<>(input.getPage(), input.getLimit());
        // 从数据库进行分页查询获取课程收藏数据
        IPage<CouseCollect> pageRecords = CouseCollectMapper.selectPage(page, queryWrapper);
        // 获取所有满足条件的数据行数
        Long totalCount = CouseCollectMapper.selectCount(queryWrapper);
        // 把CouseCollect实体转换成CouseCollect传输模型
        List<CouseCollectDto> items = Extension.copyBeanList(pageRecords.getRecords(), CouseCollectDto.class);

        DispatchItem(items);
        // 返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个课程收藏查询
     */
    @SneakyThrows
    @Override
    public CouseCollectDto Get(CouseCollectPagedInput input) {
        if (input.getId() == null) {
            return new CouseCollectDto();
        }

        PagedResult<CouseCollectDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new CouseCollectDto());
    }

    /**
     * 课程收藏创建或者修改
     */
    @SneakyThrows
    @Override
    public CouseCollectDto CreateOrEdit(CouseCollectDto input) {
        // 声明一个课程收藏实体
        CouseCollect CouseCollect = input.MapToEntity();
        // 调用数据库的增加或者修改方法
        saveOrUpdate(CouseCollect);
        // 把传输模型返回给前端
        return CouseCollect.MapToDto();
    }

    /**
     * 课程收藏删除
     */
    @Override
    public void Delete(IdInput input) {
        CouseCollect entity = CouseCollectMapper.selectById(input.getId());
        CouseCollectMapper.deleteById(entity);
    }

    /**
     * 课程收藏批量删除
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
