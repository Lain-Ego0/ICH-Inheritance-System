package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.ShopCollectDto;
import com.example.web.dto.query.ShopCollectPagedInput;
import com.example.web.entity.AppUser;
import com.example.web.entity.Shop;
import com.example.web.entity.ShopCollect;
import com.example.web.mapper.AppUserMapper;
import com.example.web.mapper.ShopCollectMapper;
import com.example.web.mapper.ShopMapper;
import com.example.web.service.ShopCollectService;
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
 * 店铺收藏功能实现类
 */
@Service
public class ShopCollectServiceImpl extends ServiceImpl<ShopCollectMapper, ShopCollect> implements ShopCollectService {

	 /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper  AppUserMapper;
    /**
     * 操作数据库的ShopCollect表mapper对象
     */
    @Autowired
    private ShopCollectMapper  ShopCollectMapper;
    @Autowired
    private ShopMapper  ShopMapper;                        

  
   /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<ShopCollect> BuilderQuery(ShopCollectPagedInput input) {
       //声明一个支持店铺收藏查询的(拉姆达)表达式
        LambdaQueryWrapper<ShopCollect> queryWrapper = Wrappers.<ShopCollect>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, ShopCollect::getId, input.getId())
                .eq(input.getCreatorId() != null, ShopCollect::getCreatorId, input.getCreatorId());
   //如果前端搜索传入查询条件则拼接查询条件

        if (input.getShopId() != null) {
            queryWrapper = queryWrapper.eq(ShopCollect::getShopId, input.getShopId());
       	 }

        if (input.getCollectUserId() != null) {
            queryWrapper = queryWrapper.eq(ShopCollect::getCollectUserId, input.getCollectUserId());
       	 }
      return queryWrapper;
    }
  
    /**
     * 处理店铺收藏对于的外键数据
     */
   private List<ShopCollectDto> DispatchItem(List<ShopCollectDto> items) throws InvocationTargetException, IllegalAccessException {
   
    for (ShopCollectDto item : items) {        
          //查询出关联的创建用户信息
           
            AppUser  CreatorAppUserEntity= AppUserMapper.selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId,item.getCreatorId())).stream().findFirst().orElse(new AppUser());
  				 item.setCreatorAppUserDto(CreatorAppUserEntity.MapToDto());           
          	            
           //查询出关联的Shop表信息           
            Shop  ShopEntity= ShopMapper.selectList(Wrappers.<Shop>lambdaQuery().eq(Shop::getId,item.getShopId())).stream().findFirst().orElse(new Shop());         
            item.setShopDto(ShopEntity.MapToDto());              
           
          	            
           //查询出关联的AppUser表信息           
            AppUser  CollectUserEntity= AppUserMapper.selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId,item.getCollectUserId())).stream().findFirst().orElse(new AppUser());         
            item.setCollectUserDto(CollectUserEntity.MapToDto());              
        }
     return items; 
   }
  
    /**
     * 店铺收藏分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<ShopCollectDto> List(ShopCollectPagedInput input) {
			//构建where条件+排序
        LambdaQueryWrapper<ShopCollect> queryWrapper = BuilderQuery(input);
       
        //按创建时间从大到小排序 最新的显示在最前面
        queryWrapper=queryWrapper.orderByDesc(ShopCollect::getCreationTime);
        //构建一个分页查询的model
        Page<ShopCollect> page = new Page<>(input.getPage(), input.getLimit());
         //从数据库进行分页查询获取店铺收藏数据
        IPage<ShopCollect> pageRecords= ShopCollectMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount= ShopCollectMapper.selectCount(queryWrapper);
        //把ShopCollect实体转换成ShopCollect传输模型
        List<ShopCollectDto> items= Extension.copyBeanList(pageRecords.getRecords(),ShopCollectDto.class);
        //计算表的数据
        items = DispatchItem(items);

      
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items,totalCount);

    }
  
    /**
     * 单个店铺收藏查询
     */
    @SneakyThrows
    @Override
    public ShopCollectDto Get(ShopCollectPagedInput input) {
       if(input.getId()==null)
        {
            return new ShopCollectDto();
        }
        PagedResult<ShopCollectDto>  pagedResult =List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new ShopCollectDto());   
    }

    /**
     *店铺收藏创建或者修改
     */
    @SneakyThrows
    @Override
    public ShopCollectDto CreateOrEdit(ShopCollectDto input) {
        //声明一个店铺收藏实体
        ShopCollect ShopCollect=input.MapToEntity();  
        //调用数据库的增加或者修改方法
        saveOrUpdate(ShopCollect);
        //把传输模型返回给前端
        return ShopCollect.MapToDto();
    }
    /**
     * 店铺收藏删除
     */
    @Override
    public void Delete(IdInput input) {
        ShopCollect entity = ShopCollectMapper.selectById(input.getId());
        ShopCollectMapper.deleteById(entity);
    }

    /**
     * 店铺收藏批量删除
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
