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
import com.example.web.dto.AppUserDto;
import com.example.web.dto.VideoExhibitionCollectDto;
import com.example.web.dto.VideoExhibitionDto;
import com.example.web.dto.query.VideoExhibitionCollectPagedInput;
import com.example.web.entity.AppUser;
import com.example.web.entity.VideoExhibition;
import com.example.web.entity.VideoExhibitionCollect;
import com.example.web.mapper.AppUserMapper;
import com.example.web.mapper.VideoExhibitionCollectMapper;
import com.example.web.mapper.VideoExhibitionMapper;
import com.example.web.service.VideoExhibitionCollectService;
import com.example.web.tools.Extension;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;

import lombok.SneakyThrows;

/**
 * 视频展览收藏功能实现类
 */
@Service
public class VideoExhibitionCollectServiceImpl extends ServiceImpl<VideoExhibitionCollectMapper, VideoExhibitionCollect>
        implements VideoExhibitionCollectService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的VideoExhibitionCollect表mapper对象
     */
    @Autowired
    private VideoExhibitionCollectMapper VideoExhibitionCollectMapper;
    @Autowired
    private VideoExhibitionMapper VideoExhibitionMapper;

    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<VideoExhibitionCollect> BuilderQuery(VideoExhibitionCollectPagedInput input) {
        // 声明一个支持视频展览收藏查询的(拉姆达)表达式
        LambdaQueryWrapper<VideoExhibitionCollect> queryWrapper = Wrappers.<VideoExhibitionCollect>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, VideoExhibitionCollect::getId, input.getId())
                .eq(input.getCreatorId() != null, VideoExhibitionCollect::getCreatorId, input.getCreatorId());
        // 如果前端搜索传入查询条件则拼接查询条件

        if (input.getCoolectUserId() != null) {
            queryWrapper = queryWrapper.eq(VideoExhibitionCollect::getCoolectUserId, input.getCoolectUserId());
        }
        if (input.getVideoExhibitionId() != null) {
            queryWrapper = queryWrapper.eq(VideoExhibitionCollect::getVideoExhibitionId, input.getVideoExhibitionId());
        }
        return queryWrapper;
    }

    /**
     * 处理视频展览收藏对于的外键数据
     */
    private List<VideoExhibitionCollectDto> DispatchItem(List<VideoExhibitionCollectDto> items)
            throws InvocationTargetException, IllegalAccessException {

        for (VideoExhibitionCollectDto item : items) {

            // 查询出关联的AppUser表信息
            AppUser CoolectUserEntity = AppUserMapper.selectById(item.getCoolectUserId());
            item.setCoolectUserDto(CoolectUserEntity != null ? CoolectUserEntity.MapToDto() : new AppUserDto());

            // 查询出关联的VideoExhibition表信息
            VideoExhibition VideoExhibitionEntity = VideoExhibitionMapper.selectById(item.getVideoExhibitionId());
            item.setVideoExhibitionDto(
                    VideoExhibitionEntity != null ? VideoExhibitionEntity.MapToDto() : new VideoExhibitionDto());
        }

        return items;
    }

    /**
     * 视频展览收藏分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<VideoExhibitionCollectDto> List(VideoExhibitionCollectPagedInput input) {
        // 构建where条件+排序
        LambdaQueryWrapper<VideoExhibitionCollect> queryWrapper = BuilderQuery(input);

        // 按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(VideoExhibitionCollect::getCreationTime);
        // 构建一个分页查询的model
        Page<VideoExhibitionCollect> page = new Page<>(input.getPage(), input.getLimit());
        // 从数据库进行分页查询获取视频展览收藏数据
        IPage<VideoExhibitionCollect> pageRecords = VideoExhibitionCollectMapper.selectPage(page, queryWrapper);
        // 获取所有满足条件的数据行数
        Long totalCount = VideoExhibitionCollectMapper.selectCount(queryWrapper);
        // 把VideoExhibitionCollect实体转换成VideoExhibitionCollect传输模型
        List<VideoExhibitionCollectDto> items = Extension.copyBeanList(pageRecords.getRecords(),
                VideoExhibitionCollectDto.class);

        DispatchItem(items);
        // 返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个视频展览收藏查询
     */
    @SneakyThrows
    @Override
    public VideoExhibitionCollectDto Get(VideoExhibitionCollectPagedInput input) {
        if (input.getId() == null) {
            return new VideoExhibitionCollectDto();
        }

        PagedResult<VideoExhibitionCollectDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new VideoExhibitionCollectDto());
    }

    /**
     * 视频展览收藏创建或者修改
     */
    @SneakyThrows
    @Override
    public VideoExhibitionCollectDto CreateOrEdit(VideoExhibitionCollectDto input) {
        // 声明一个视频展览收藏实体
        VideoExhibitionCollect VideoExhibitionCollect = input.MapToEntity();
        // 调用数据库的增加或者修改方法
        saveOrUpdate(VideoExhibitionCollect);
        // 把传输模型返回给前端
        return VideoExhibitionCollect.MapToDto();
    }

    /**
     * 视频展览收藏删除
     */
    @Override
    public void Delete(IdInput input) {
        VideoExhibitionCollect entity = VideoExhibitionCollectMapper.selectById(input.getId());
        VideoExhibitionCollectMapper.deleteById(entity);
    }

    /**
     * 视频展览收藏批量删除
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
