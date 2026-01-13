package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.OrderReturnDto;
import com.example.web.dto.query.OrderReturnPagedInput;
import com.example.web.entity.OrderReturn;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
/**
 * 订单退货功能的Service接口的定义清单
 */
public interface OrderReturnService extends IService<OrderReturn> {

    /**
     * 订单退货的分页查询方法接口定义
     */
    public PagedResult<OrderReturnDto> List(OrderReturnPagedInput input) ;
    /**
     * 订单退货的新增或者修改方法接口定义
     */
    public OrderReturnDto CreateOrEdit(OrderReturnDto input);

     /**
     * 获取订单退货信息
     */
    public OrderReturnDto Get(OrderReturnPagedInput input);

    /**
     * 撤销申请
     */
    @SneakyThrows
    void Discard(OrderReturnDto input);
    /**
     * 商家处理
     */
    @SneakyThrows
    void SellerDispatch(OrderReturnDto input);

    /**
     * 订单退货删除
     */
    public void Delete(IdInput input);

    /**
     * 订单退货批量删除
     */
    public void BatchDelete(IdsInput input);
  
	  /**
     * 订单退货导出
     */
    public void Export(@RequestParam String query, HttpServletResponse response) throws IOException;

}
