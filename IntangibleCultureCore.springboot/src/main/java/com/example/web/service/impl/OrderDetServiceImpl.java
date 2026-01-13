package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.OrderDetDto;
import com.example.web.dto.query.OrderDetPagedInput;
import com.example.web.entity.AppUser;
import com.example.web.entity.Good;
import com.example.web.entity.OrderDet;
import com.example.web.entity.OrderInfo;
import com.example.web.mapper.AppUserMapper;
import com.example.web.mapper.GoodMapper;
import com.example.web.mapper.OrderDetMapper;
import com.example.web.mapper.OrderInfoMapper;
import com.example.web.service.OrderDetService;
import com.example.web.tools.Extension;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 订单明细功能实现类
 */
@Service
public class OrderDetServiceImpl extends ServiceImpl<OrderDetMapper, OrderDet> implements OrderDetService {

	 /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper  AppUserMapper;
    /**
     * 操作数据库的OrderDet表mapper对象
     */
    @Autowired
    private OrderDetMapper  OrderDetMapper;
    @Autowired
    private OrderInfoMapper  OrderInfoMapper;                        
    @Autowired
    private GoodMapper  GoodMapper;                        

  
   /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<OrderDet> BuilderQuery(OrderDetPagedInput input) {
       //声明一个支持订单明细查询的(拉姆达)表达式
        LambdaQueryWrapper<OrderDet> queryWrapper = Wrappers.<OrderDet>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, OrderDet::getId, input.getId())
                .eq(input.getCreatorId() != null, OrderDet::getCreatorId, input.getCreatorId());
   //如果前端搜索传入查询条件则拼接查询条件

        if (input.getOrderId() != null) {
            queryWrapper = queryWrapper.eq(OrderDet::getOrderId, input.getOrderId());
       	 }

        if (input.getGoodId() != null) {
            queryWrapper = queryWrapper.eq(OrderDet::getGoodId, input.getGoodId());
       	 }
      return queryWrapper;
    }
  
    /**
     * 处理订单明细对于的外键数据
     */
   private List<OrderDetDto> DispatchItem(List<OrderDetDto> items) throws InvocationTargetException, IllegalAccessException {
   
    for (OrderDetDto item : items) {        
          //查询出关联的创建用户信息
           
            AppUser  CreatorAppUserEntity= AppUserMapper.selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId,item.getCreatorId())).stream().findFirst().orElse(new AppUser());
  				 item.setCreatorAppUserDto(CreatorAppUserEntity.MapToDto());           
          	            
           //查询出关联的OrderInfo表信息           
            OrderInfo  OrderEntity= OrderInfoMapper.selectList(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getId,item.getOrderId())).stream().findFirst().orElse(new OrderInfo());         
            item.setOrderDto(OrderEntity.MapToDto());              
           
          	            
           //查询出关联的Good表信息           
            Good  GoodEntity= GoodMapper.selectList(Wrappers.<Good>lambdaQuery().eq(Good::getId,item.getGoodId())).stream().findFirst().orElse(new Good());         
            item.setGoodDto(GoodEntity.MapToDto());              
        }
     return items; 
   }
  
    /**
     * 订单明细分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<OrderDetDto> List(OrderDetPagedInput input) {
			//构建where条件+排序
        LambdaQueryWrapper<OrderDet> queryWrapper = BuilderQuery(input);
       
        //按创建时间从大到小排序 最新的显示在最前面
        queryWrapper=queryWrapper.orderByDesc(OrderDet::getCreationTime);
        //构建一个分页查询的model
        Page<OrderDet> page = new Page<>(input.getPage(), input.getLimit());
         //从数据库进行分页查询获取订单明细数据
        IPage<OrderDet> pageRecords= OrderDetMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount= OrderDetMapper.selectCount(queryWrapper);
        //把OrderDet实体转换成OrderDet传输模型
        List<OrderDetDto> items= Extension.copyBeanList(pageRecords.getRecords(),OrderDetDto.class);
        //计算表的数据
        items = DispatchItem(items);

      
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items,totalCount);

    }
  
    /**
     * 单个订单明细查询
     */
    @SneakyThrows
    @Override
    public OrderDetDto Get(OrderDetPagedInput input) {
       if(input.getId()==null)
        {
            return new OrderDetDto();
        }
        PagedResult<OrderDetDto>  pagedResult =List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new OrderDetDto());   
    }

    /**
     *订单明细创建或者修改
     */
    @SneakyThrows
    @Override
    public OrderDetDto CreateOrEdit(OrderDetDto input) {
        //声明一个订单明细实体
        OrderDet OrderDet=input.MapToEntity();  
        //调用数据库的增加或者修改方法
        saveOrUpdate(OrderDet);
        //把传输模型返回给前端
        return OrderDet.MapToDto();
    }
    /**
     * 订单明细删除
     */
    @Override
    public void Delete(IdInput input) {
        OrderDet entity = OrderDetMapper.selectById(input.getId());
        OrderDetMapper.deleteById(entity);
    }

    /**
     * 订单明细批量删除
     */
    @Override
    public void BatchDelete(IdsInput input) {
        for (Integer id : input.getIds()) {
            IdInput idInput = new IdInput();
            idInput.setId(id);
            Delete(idInput);
        }
    }
}
