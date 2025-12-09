package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.CouseStepDto;
import com.example.web.dto.query.CouseStepPagedInput;
import com.example.web.entity.CouseStep;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;

/**
 * 课程章节功能的Service接口的定义清单
 */
public interface CouseStepService extends IService<CouseStep> {

    /**
     * 课程章节的分页查询方法接口定义
     */
    public PagedResult<CouseStepDto> List(CouseStepPagedInput input);

    /**
     * 课程章节的新增或者修改方法接口定义
     */
    public CouseStepDto CreateOrEdit(CouseStepDto input);

    /**
     * 获取课程章节信息
     */
    public CouseStepDto Get(CouseStepPagedInput input);

    /**
     * 课程章节删除
     */
    public void Delete(IdInput input);

    /**
     * 课程章节批量删除
     */
    public void BatchDelete(IdsInput input);

}
