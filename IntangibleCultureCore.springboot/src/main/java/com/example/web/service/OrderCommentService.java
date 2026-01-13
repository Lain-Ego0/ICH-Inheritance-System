package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.OrderCommentDto;
import com.example.web.dto.query.OrderCommentPagedInput;
import com.example.web.entity.OrderComment;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
/**
 * 订单评价功能的Service接口的定义清单
 */
public interface OrderCommentService extends IService<OrderComment> {

    /**
     * 订单评价的分页查询方法接口定义
     */
    public PagedResult<OrderCommentDto> List(OrderCommentPagedInput input) ;
    /**
     * 订单评价的新增或者修改方法接口定义
     */
    public OrderCommentDto CreateOrEdit(OrderCommentDto input);

     /**
     * 获取订单评价信息
     */
    public OrderCommentDto Get(OrderCommentPagedInput input);
 	 /**
     * 订单评价删除
     */
    public void Delete(IdInput input);

    /**
     * 订单评价批量删除
     */
    public void BatchDelete(IdsInput input);
  
	  /**
     * 订单评价导出
     */
    public void Export(@RequestParam String query, HttpServletResponse response) throws IOException;

    /**
     * 订单评价
     */
    void OrderComment(List<OrderCommentDto> input) throws InvocationTargetException, IllegalAccessException;
}
