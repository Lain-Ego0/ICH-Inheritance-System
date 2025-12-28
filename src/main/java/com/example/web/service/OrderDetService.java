package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.OrderDetDto;
import com.example.web.dto.query.OrderDetPagedInput;
import com.example.web.entity.OrderDet;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;

/**
 * 订单明细功能的Service接口的定义清单
 */
public interface OrderDetService extends IService<OrderDet> {

    /**
     * 订单明细的分页查询方法接口定义
     */
    public PagedResult<OrderDetDto> List(OrderDetPagedInput input) ;
    /**
     * 订单明细的新增或者修改方法接口定义
     */
    public OrderDetDto CreateOrEdit(OrderDetDto input);

     /**
     * 获取订单明细信息
     */
    public OrderDetDto Get(OrderDetPagedInput input);
 	 /**
     * 订单明细删除
     */
    public void Delete(IdInput input);

    /**
     * 订单明细批量删除
     */
    public void BatchDelete(IdsInput input);
  

}
