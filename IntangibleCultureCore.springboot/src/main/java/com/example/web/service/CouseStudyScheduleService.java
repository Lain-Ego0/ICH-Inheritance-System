package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.CouseStudyScheduleDto;
import com.example.web.dto.query.CouseStudySchedulePagedInput;
import com.example.web.entity.CouseStudySchedule;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;

/**
 * 课程学习进度功能的Service接口的定义清单
 */
public interface CouseStudyScheduleService extends IService<CouseStudySchedule> {

    /**
     * 课程学习进度的分页查询方法接口定义
     */
    public PagedResult<CouseStudyScheduleDto> List(CouseStudySchedulePagedInput input);

    /**
     * 课程学习进度的新增或者修改方法接口定义
     */
    public CouseStudyScheduleDto CreateOrEdit(CouseStudyScheduleDto input);

    /**
     * 获取课程学习进度信息
     */
    public CouseStudyScheduleDto Get(CouseStudySchedulePagedInput input);

    /**
     * 课程学习进度删除
     */
    public void Delete(IdInput input);

    /**
     * 课程学习进度批量删除
     */
    public void BatchDelete(IdsInput input);

    /**
     * 更新时长
     */
    public CouseStudyScheduleDto UpdateTotalDurtion(CouseStudyScheduleDto input);
}
