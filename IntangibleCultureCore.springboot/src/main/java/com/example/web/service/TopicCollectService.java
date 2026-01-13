package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.TopicCollectDto;
import com.example.web.dto.query.TopicCollectPagedInput;
import com.example.web.entity.TopicCollect;
import com.example.web.tools.dto.PagedResult;


/**
 * 话题收藏功能的Service接口的定义清单
 */
public interface TopicCollectService extends IService<TopicCollect> {

    /**
     * 话题收藏的分页查询方法接口定义
     */
    public PagedResult<TopicCollectDto> List(TopicCollectPagedInput input) ;
    /**
     * 话题收藏的新增或者修改方法接口定义
     */
    public TopicCollectDto CreateOrEdit(TopicCollectDto input);

     /**
     * 获取话题收藏信息
     */
    public TopicCollectDto Get(TopicCollectPagedInput input);


}
