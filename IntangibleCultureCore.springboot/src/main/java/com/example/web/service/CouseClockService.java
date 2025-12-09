package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.CouseClockDto;
import com.example.web.dto.query.CouseClockPagedInput;
import com.example.web.entity.CouseClock;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;

/**
 * 课程打卡功能的Service接口的定义清单
 */
public interface CouseClockService extends IService<CouseClock> {

    /**
     * 课程打卡的分页查询方法接口定义
     */
    public PagedResult<CouseClockDto> List(CouseClockPagedInput input);

    /**
     * 课程打卡的新增或者修改方法接口定义
     */
    public CouseClockDto CreateOrEdit(CouseClockDto input);

    /**
     * 获取课程打卡信息
     */
    public CouseClockDto Get(CouseClockPagedInput input);

    /**
     * 课程打卡删除
     */
    public void Delete(IdInput input);

    /**
     * 课程打卡批量删除
     */
    public void BatchDelete(IdsInput input);

}
