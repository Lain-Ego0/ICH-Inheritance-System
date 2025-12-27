package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.CulturalTypeDto;
import com.example.web.dto.query.CulturalTypePagedInput;
import com.example.web.entity.CulturalType;
import com.example.web.mapper.AppUserMapper;
import com.example.web.mapper.CulturalTypeMapper;
import com.example.web.service.CulturalTypeService;
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
 * 非遗文化类别功能实现类
 */
@Service
public class CulturalTypeServiceImpl extends ServiceImpl<CulturalTypeMapper, CulturalType> implements CulturalTypeService {

	 /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的CulturalType表mapper对象
     */
    @Autowired
    private CulturalTypeMapper CulturalTypeMapper;

  
   /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<CulturalType> BuilderQuery(CulturalTypePagedInput input) {
       //声明一个支持非遗文化类别查询的(拉姆达)表达式
        LambdaQueryWrapper<CulturalType> queryWrapper = Wrappers.<CulturalType>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, CulturalType::getId, input.getId())
                .eq(input.getCreatorId() != null, CulturalType::getCreatorId, input.getCreatorId());
   //如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getName())) {
             queryWrapper = queryWrapper.like(CulturalType::getName, input.getName());
       	 }
      return queryWrapper;
    }
  
    /**
     * 处理非遗文化类别对于的外键数据
     */
   private List<CulturalTypeDto> DispatchItem(List<CulturalTypeDto> items) throws InvocationTargetException, IllegalAccessException {
          
       for (CulturalTypeDto item : items) {       }
       
     return items; 
   }
  
    /**
     * 非遗文化类别分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<CulturalTypeDto> List(CulturalTypePagedInput input) {
			//构建where条件+排序
        LambdaQueryWrapper<CulturalType> queryWrapper = BuilderQuery(input);
       
        //按创建时间从大到小排序 最新的显示在最前面
        queryWrapper=queryWrapper.orderByDesc(CulturalType::getCreationTime);
        //构建一个分页查询的model
        Page<CulturalType> page = new Page<>(input.getPage(), input.getLimit());
         //从数据库进行分页查询获取非遗文化类别数据
        IPage<CulturalType> pageRecords= CulturalTypeMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount= CulturalTypeMapper.selectCount(queryWrapper);
        //把CulturalType实体转换成CulturalType传输模型
        List<CulturalTypeDto> items= Extension.copyBeanList(pageRecords.getRecords(),CulturalTypeDto.class);

		   DispatchItem(items);
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items,totalCount);

    }
  
    /**
     * 单个非遗文化类别查询
     */
    @SneakyThrows
    @Override
    public CulturalTypeDto Get(CulturalTypePagedInput input) {
       if(input.getId()==null)
        {
         return new CulturalTypeDto();
        }
      
       PagedResult<CulturalTypeDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new CulturalTypeDto()); 
    }

    /**
     *非遗文化类别创建或者修改
     */
    @SneakyThrows
    @Override
    public CulturalTypeDto CreateOrEdit(CulturalTypeDto input) {
        //声明一个非遗文化类别实体
        CulturalType CulturalType=input.MapToEntity();  
        //调用数据库的增加或者修改方法
        saveOrUpdate(CulturalType);
        //把传输模型返回给前端
        return CulturalType.MapToDto();
    }
    /**
     * 非遗文化类别删除
     */
    @Override
    public void Delete(IdInput input) {
        CulturalType entity = CulturalTypeMapper.selectById(input.getId());
        CulturalTypeMapper.deleteById(entity);
    }

    /**
     * 非遗文化类别批量删除
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
