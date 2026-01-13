package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.TopicTypeDto;
import com.example.web.dto.query.TopicTypePagedInput;
import com.example.web.entity.TopicType;
import com.example.web.tools.dto.PagedResult;


/**
 * 话题类型功能的Service接口的定义清单
 */
public interface TopicTypeService extends IService<TopicType> {

    /**
     * 话题类型的分页查询方法接口定义
     */
    public PagedResult<TopicTypeDto> List(TopicTypePagedInput input) ;
    /**
     * 话题类型的新增或者修改方法接口定义
     */
    public TopicTypeDto CreateOrEdit(TopicTypeDto input);

     /**
     * 获取话题类型信息
     */
    public TopicTypeDto Get(TopicTypePagedInput input);


}
