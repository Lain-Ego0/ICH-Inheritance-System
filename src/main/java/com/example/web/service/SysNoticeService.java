package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.SysNoticeDto;
import com.example.web.dto.query.SysNoticePagedInput;
import com.example.web.entity.SysNotice;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;

/**
 * 系统通知功能的Service接口的定义清单
 */
public interface SysNoticeService extends IService<SysNotice> {

    /**
     * 系统通知的分页查询方法接口定义
     */
    public PagedResult<SysNoticeDto> List(SysNoticePagedInput input) ;
    /**
     * 系统通知的新增或者修改方法接口定义
     */
    public SysNoticeDto CreateOrEdit(SysNoticeDto input);

     /**
     * 获取系统通知信息
     */
    public SysNoticeDto Get(SysNoticePagedInput input);
 	 /**
     * 系统通知删除
     */
    public void Delete(IdInput input);

    /**
     * 系统通知批量删除
     */
    public void BatchDelete(IdsInput input);
  

}
