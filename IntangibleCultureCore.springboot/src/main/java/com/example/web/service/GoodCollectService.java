package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.GoodCollectDto;
import com.example.web.dto.query.GoodCollectPagedInput;
import com.example.web.entity.GoodCollect;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**
 * 文创品收藏功能的Service接口的定义清单
 */
public interface GoodCollectService extends IService<GoodCollect> {

    /**
     * 文创品收藏的分页查询方法接口定义
     */
    public PagedResult<GoodCollectDto> List(GoodCollectPagedInput input);

    /**
     * 文创品收藏的新增或者修改方法接口定义
     */
    public GoodCollectDto CreateOrEdit(GoodCollectDto input);

    /**
     * 获取文创品收藏信息
     */
    public GoodCollectDto Get(GoodCollectPagedInput input);

    /**
     * 文创品收藏删除
     */
    public void Delete(IdInput input);

    /**
     * 文创品收藏批量删除
     */
    public void BatchDelete(IdsInput input);

    /**
     * 文创品收藏导出
     */
    public void Export(@RequestParam String query, HttpServletResponse response) throws IOException;

}
