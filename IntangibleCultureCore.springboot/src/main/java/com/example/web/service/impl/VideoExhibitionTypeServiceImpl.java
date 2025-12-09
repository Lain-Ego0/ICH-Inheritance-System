package com.example.web.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.SysConst;
import com.example.web.dto.*;
import com.example.web.dto.query.*;
import com.example.web.entity.*;
import com.example.web.mapper.*;
import com.example.web.enums.*;
import com.example.web.service.*;
import com.example.web.tools.dto.*;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import java.io.IOException;
import com.example.web.tools.*;
import java.text.DecimalFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * 视频展览类型功能实现类
 */
@Service
public class VideoExhibitionTypeServiceImpl extends ServiceImpl<VideoExhibitionTypeMapper, VideoExhibitionType> implements VideoExhibitionTypeService {

	 /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的VideoExhibitionType表mapper对象
     */
    @Autowired
    private VideoExhibitionTypeMapper VideoExhibitionTypeMapper;

  
   /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<VideoExhibitionType> BuilderQuery(VideoExhibitionTypePagedInput input) {
       //声明一个支持视频展览类型查询的(拉姆达)表达式
        LambdaQueryWrapper<VideoExhibitionType> queryWrapper = Wrappers.<VideoExhibitionType>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, VideoExhibitionType::getId, input.getId())
                .eq(input.getCreatorId() != null, VideoExhibitionType::getCreatorId, input.getCreatorId());
   //如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getName())) {
             queryWrapper = queryWrapper.like(VideoExhibitionType::getName, input.getName());
       	 }
      return queryWrapper;
    }
  
    /**
     * 处理视频展览类型对于的外键数据
     */
   private List<VideoExhibitionTypeDto> DispatchItem(List<VideoExhibitionTypeDto> items) throws InvocationTargetException, IllegalAccessException {
          
       for (VideoExhibitionTypeDto item : items) {       }
       
     return items; 
   }
  
    /**
     * 视频展览类型分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<VideoExhibitionTypeDto> List(VideoExhibitionTypePagedInput input) {
			//构建where条件+排序
        LambdaQueryWrapper<VideoExhibitionType> queryWrapper = BuilderQuery(input);
       
        //按创建时间从大到小排序 最新的显示在最前面
        queryWrapper=queryWrapper.orderByDesc(VideoExhibitionType::getCreationTime);
        //构建一个分页查询的model
        Page<VideoExhibitionType> page = new Page<>(input.getPage(), input.getLimit());
         //从数据库进行分页查询获取视频展览类型数据
        IPage<VideoExhibitionType> pageRecords= VideoExhibitionTypeMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount= VideoExhibitionTypeMapper.selectCount(queryWrapper);
        //把VideoExhibitionType实体转换成VideoExhibitionType传输模型
        List<VideoExhibitionTypeDto> items= Extension.copyBeanList(pageRecords.getRecords(),VideoExhibitionTypeDto.class);

		   DispatchItem(items);
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items,totalCount);

    }
  
    /**
     * 单个视频展览类型查询
     */
    @SneakyThrows
    @Override
    public VideoExhibitionTypeDto Get(VideoExhibitionTypePagedInput input) {
       if(input.getId()==null)
        {
         return new VideoExhibitionTypeDto();
        }
      
       PagedResult<VideoExhibitionTypeDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new VideoExhibitionTypeDto()); 
    }

    /**
     *视频展览类型创建或者修改
     */
    @SneakyThrows
    @Override
    public VideoExhibitionTypeDto CreateOrEdit(VideoExhibitionTypeDto input) {
        //声明一个视频展览类型实体
        VideoExhibitionType VideoExhibitionType=input.MapToEntity();  
        //调用数据库的增加或者修改方法
        saveOrUpdate(VideoExhibitionType);
        //把传输模型返回给前端
        return VideoExhibitionType.MapToDto();
    }
    /**
     * 视频展览类型删除
     */
    @Override
    public void Delete(IdInput input) {
        VideoExhibitionType entity = VideoExhibitionTypeMapper.selectById(input.getId());
        VideoExhibitionTypeMapper.deleteById(entity);
    }

    /**
     * 视频展览类型批量删除
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
