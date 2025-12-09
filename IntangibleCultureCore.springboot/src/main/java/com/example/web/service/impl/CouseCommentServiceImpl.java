package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.AppUserDto;
import com.example.web.dto.CouseCommentDto;
import com.example.web.dto.CouseDto;
import com.example.web.dto.CouseStepDto;
import com.example.web.dto.query.CouseCommentPagedInput;
import com.example.web.entity.AppUser;
import com.example.web.entity.Couse;
import com.example.web.entity.CouseComment;
import com.example.web.entity.CouseStep;
import com.example.web.mapper.AppUserMapper;
import com.example.web.mapper.CouseCommentMapper;
import com.example.web.mapper.CouseMapper;
import com.example.web.mapper.CouseStepMapper;
import com.example.web.service.CouseCommentService;
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
 * 课程评价功能实现类
 */
@Service
public class CouseCommentServiceImpl extends ServiceImpl<CouseCommentMapper, CouseComment>
        implements CouseCommentService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的CouseComment表mapper对象
     */
    @Autowired
    private CouseCommentMapper CouseCommentMapper;
    @Autowired
    private CouseMapper CouseMapper;

    @Autowired
    private CouseStepMapper CouseStepMapper;

    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<CouseComment> BuilderQuery(CouseCommentPagedInput input) {
        // 声明一个支持课程评价查询的(拉姆达)表达式
        LambdaQueryWrapper<CouseComment> queryWrapper = Wrappers.<CouseComment>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, CouseComment::getId, input.getId())
                .eq(input.getCreatorId() != null, CouseComment::getCreatorId, input.getCreatorId());
        // 如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getComment())) {
            queryWrapper = queryWrapper.like(CouseComment::getComment, input.getComment());
        }

        if (input.getCouseId() != null) {
            queryWrapper = queryWrapper.eq(CouseComment::getCouseId, input.getCouseId());
        }

        if (input.getUserId() != null) {
            queryWrapper = queryWrapper.eq(CouseComment::getUserId, input.getUserId());
        }

        if (input.getTeachUserId() != null) {
            // 查询对应的课程
            List<Couse> couseList = CouseMapper
                    .selectList(Wrappers.<Couse>lambdaQuery().eq(Couse::getCreatorId, input.getTeachUserId()));
            if (couseList.size() > 0) {
                queryWrapper = queryWrapper.in(CouseComment::getCouseId,
                        couseList.stream().map(Couse::getId).collect(Collectors.toList()));
            } else {
                queryWrapper = queryWrapper.eq(CouseComment::getCouseId, 0);
            }
        }
        return queryWrapper;
    }

    /**
     * 处理课程评价对于的外键数据
     */
    private List<CouseCommentDto> DispatchItem(List<CouseCommentDto> items)
            throws InvocationTargetException, IllegalAccessException {

        for (CouseCommentDto item : items) {
            // 查询创建人
            AppUser CreateUserEntity = AppUserMapper
                    .selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getCreatorId())).stream()
                    .findFirst().orElse(new AppUser());
            item.setCreatorAppUserDto(CreateUserEntity.MapToDto());
            // 查询出关联的AppUser表信息
            AppUser UserEntity = AppUserMapper.selectById(item.getUserId());
            item.setUserDto(UserEntity != null ? UserEntity.MapToDto() : new AppUserDto());

            // 查询出关联的Couse表信息
            Couse CouseEntity = CouseMapper.selectById(item.getCouseId());
            item.setCouseDto(CouseEntity != null ? CouseEntity.MapToDto() : new CouseDto());

            // 查询出关联的CouseStep表信息
            CouseStep CouseStepEntity = CouseStepMapper.selectById(item.getCouseStepId());
            item.setCouseStepDto(CouseStepEntity != null ? CouseStepEntity.MapToDto() : new CouseStepDto());
        }

        return items;
    }

    /**
     * 课程评价分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<CouseCommentDto> List(CouseCommentPagedInput input) {
        // 构建where条件+排序
        LambdaQueryWrapper<CouseComment> queryWrapper = BuilderQuery(input);

        // 按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(CouseComment::getCreationTime);
        // 构建一个分页查询的model
        Page<CouseComment> page = new Page<>(input.getPage(), input.getLimit());
        // 从数据库进行分页查询获取课程评价数据
        IPage<CouseComment> pageRecords = CouseCommentMapper.selectPage(page, queryWrapper);
        // 获取所有满足条件的数据行数
        Long totalCount = CouseCommentMapper.selectCount(queryWrapper);
        // 把CouseComment实体转换成CouseComment传输模型
        List<CouseCommentDto> items = Extension.copyBeanList(pageRecords.getRecords(), CouseCommentDto.class);

        DispatchItem(items);
        // 返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个课程评价查询
     */
    @SneakyThrows
    @Override
    public CouseCommentDto Get(CouseCommentPagedInput input) {
        if (input.getId() == null) {
            return new CouseCommentDto();
        }

        PagedResult<CouseCommentDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new CouseCommentDto());
    }

    /**
     * 课程评价创建或者修改
     */
    @SneakyThrows
    @Override
    public CouseCommentDto CreateOrEdit(CouseCommentDto input) {
        // 声明一个课程评价实体
        CouseComment CouseComment = input.MapToEntity();
        // 调用数据库的增加或者修改方法
        saveOrUpdate(CouseComment);
        // 把传输模型返回给前端
        return CouseComment.MapToDto();
    }

    /**
     * 课程评价删除
     */
    @Override
    public void Delete(IdInput input) {
        CouseComment entity = CouseCommentMapper.selectById(input.getId());
        CouseCommentMapper.deleteById(entity);
    }

    /**
     * 课程评价批量删除
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
