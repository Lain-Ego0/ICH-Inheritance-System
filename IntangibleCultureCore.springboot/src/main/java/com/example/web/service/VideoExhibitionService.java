package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.VideoExhibitionDto;
import com.example.web.dto.query.VideoExhibitionPagedInput;
import com.example.web.entity.VideoExhibition;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;

/**
 * 视频展览功能的Service接口的定义清单
 */
public interface VideoExhibitionService extends IService<VideoExhibition> {

    /**
     * 视频展览的分页查询方法接口定义
     */
    public PagedResult<VideoExhibitionDto> List(VideoExhibitionPagedInput input);

    /**
     * 视频展览的新增或者修改方法接口定义
     */
    public VideoExhibitionDto CreateOrEdit(VideoExhibitionDto input);

    /**
     * 获取视频展览信息
     */
    public VideoExhibitionDto Get(VideoExhibitionPagedInput input);

    /**
     * 视频展览删除
     */
    public void Delete(IdInput input);

    /**
     * 视频展览批量删除
     */
    public void BatchDelete(IdsInput input);

    /**
     * 基于内容推荐
     */
    public PagedResult<VideoExhibitionDto> RecommendByContent(VideoExhibitionPagedInput input);
}
