package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.CouseDto;
import com.example.web.dto.query.CousePagedInput;
import com.example.web.entity.Couse;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;

import java.util.List;

/**
 * 课程功能的Service接口的定义清单
 */
public interface CouseService extends IService<Couse> {

    /**
     * 课程的分页查询方法接口定义
     */
    public PagedResult<CouseDto> List(CousePagedInput input);

    /**
     * 课程的新增或者修改方法接口定义
     */
    public CouseDto CreateOrEdit(CouseDto input);

    /**
     * 获取课程信息
     */
    public CouseDto Get(CousePagedInput input);

    /**
     * 课程删除
     */
    public void Delete(IdInput input);

    /**
     * 课程批量删除
     */
    public void BatchDelete(IdsInput input);

    /**
     * 推荐用户可能喜欢的课程 协同过滤+内容推荐
     */
    public PagedResult<CouseDto> Recommend(CouseDto input);

    /**
     * 统计课程报名人数
     */
    public List<Object> CouseEnrollCountEchart();
}
