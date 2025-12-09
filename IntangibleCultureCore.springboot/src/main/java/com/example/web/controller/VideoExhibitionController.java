package com.example.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.web.dto.VideoExhibitionDto;
import com.example.web.dto.query.VideoExhibitionPagedInput;
import com.example.web.mapper.VideoExhibitionMapper;
import com.example.web.service.VideoExhibitionService;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;

import lombok.SneakyThrows;

/**
 * 视频展览控制器
 */
@RestController()
@RequestMapping("/VideoExhibition")
public class VideoExhibitionController {
    @Autowired()
    private VideoExhibitionService VideoExhibitionService;
    @Autowired()
    private VideoExhibitionMapper VideoExhibitionMapper;

    /**
     * 视频展览分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<VideoExhibitionDto> List(@RequestBody VideoExhibitionPagedInput input) {
        return VideoExhibitionService.List(input);
    }

    /**
     * 单个视频展览查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public VideoExhibitionDto Get(@RequestBody VideoExhibitionPagedInput input) {

        return VideoExhibitionService.Get(input);
    }

    /**
     * 视频展览创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public VideoExhibitionDto CreateOrEdit(@RequestBody VideoExhibitionDto input) throws Exception {
        return VideoExhibitionService.CreateOrEdit(input);
    }

    /**
     * 视频展览删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input) {
        VideoExhibitionService.Delete(input);
    }

    /**
     * 视频展览批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input) {
        VideoExhibitionService.BatchDelete(input);
    }

    /**
     * 基于内容推荐
     */
    @RequestMapping(value = "/RecommendByContent", method = RequestMethod.POST)
    public PagedResult<VideoExhibitionDto> RecommendByContent(@RequestBody VideoExhibitionPagedInput input) {
        return VideoExhibitionService.RecommendByContent(input);
    }

}
