package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.UserAddressDto;
import com.example.web.dto.query.UserAddressPagedInput;
import com.example.web.entity.AppUser;
import com.example.web.entity.UserAddress;
import com.example.web.mapper.AppUserMapper;
import com.example.web.mapper.UserAddressMapper;
import com.example.web.service.UserAddressService;
import com.example.web.tools.Extension;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
/**
 * 收货地址功能实现类
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {

	 /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper  AppUserMapper;
    /**
     * 操作数据库的UserAddress表mapper对象
     */
    @Autowired
    private UserAddressMapper  UserAddressMapper;

  
   /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<UserAddress> BuilderQuery(UserAddressPagedInput input) {
       //声明一个支持收货地址查询的(拉姆达)表达式
        LambdaQueryWrapper<UserAddress> queryWrapper = Wrappers.<UserAddress>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, UserAddress::getId, input.getId())
                .eq(input.getCreatorId() != null, UserAddress::getCreatorId, input.getCreatorId());
   //如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getNameLike())) {
             queryWrapper = queryWrapper.like(UserAddress::getName, input.getNameLike());
       	 }
        if (Extension.isNotNullOrEmpty(input.getPhoneLike())) {
             queryWrapper = queryWrapper.like(UserAddress::getPhone, input.getPhoneLike());
       	 }
        if (Extension.isNotNullOrEmpty(input.getProviceCityAreaLike())) {
            queryWrapper = queryWrapper.like(UserAddress::getProviceCityArea, input.getProviceCityAreaLike());
        }
        if (Extension.isNotNullOrEmpty(input.getAddressLike())) {
            queryWrapper = queryWrapper.like(UserAddress::getAddress, input.getAddressLike());
        }
        if (Extension.isNotNullOrEmpty(input.getPhoneLike())) {
            queryWrapper = queryWrapper.like(UserAddress::getPhone, input.getPhoneLike());
        }


        if (Extension.isNotNullOrEmpty(input.getSexLike())) {
             queryWrapper = queryWrapper.like(UserAddress::getSex, input.getSexLike());
       	 }
        if (Extension.isNotNullOrEmpty(input.getHouseNumberLike())) {
             queryWrapper = queryWrapper.like(UserAddress::getHouseNumber, input.getHouseNumberLike());
       	 }

        if (input.getBelongUserId() != null) {
            queryWrapper = queryWrapper.eq(UserAddress::getBelongUserId, input.getBelongUserId());
       	 }
      return queryWrapper;
    }
  
    /**
     * 处理收货地址对于的外键数据
     */
   private List<UserAddressDto> DispatchItem(List<UserAddressDto> items) throws InvocationTargetException, IllegalAccessException {
   
    for (UserAddressDto item : items) {        
          //查询出关联的创建用户信息
           
            AppUser  CreatorAppUserEntity= AppUserMapper.selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId,item.getCreatorId())).stream().findFirst().orElse(new AppUser());
  				 item.setCreatorAppUserDto(CreatorAppUserEntity.MapToDto());           
          	            
           //查询出关联的AppUser表信息           
            AppUser  BelongUserEntity= AppUserMapper.selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId,item.getBelongUserId())).stream().findFirst().orElse(new AppUser());         
            item.setBelongUserDto(BelongUserEntity.MapToDto());              
        }
     return items; 
   }
  
    /**
     * 收货地址分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<UserAddressDto> List(UserAddressPagedInput input) {
			//构建where条件+排序
        LambdaQueryWrapper<UserAddress> queryWrapper = BuilderQuery(input);
       
        //按创建时间从大到小排序 最新的显示在最前面
        queryWrapper=queryWrapper.orderByDesc(UserAddress::getCreationTime);
        //构建一个分页查询的model
        Page<UserAddress> page = new Page<>(input.getPage(), input.getLimit());
         //从数据库进行分页查询获取收货地址数据
        IPage<UserAddress> pageRecords= UserAddressMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount= UserAddressMapper.selectCount(queryWrapper);
        //把UserAddress实体转换成UserAddress传输模型
        List<UserAddressDto> items= Extension.copyBeanList(pageRecords.getRecords(),UserAddressDto.class);
        //计算表的数据
        items = DispatchItem(items);

      
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items,totalCount);

    }
  
    /**
     * 单个收货地址查询
     */
    @SneakyThrows
    @Override
    public UserAddressDto Get(UserAddressPagedInput input) {
       if(input.getId()==null)
        {
            return new UserAddressDto();
        }
        PagedResult<UserAddressDto>  pagedResult =List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new UserAddressDto());   
    }

    /**
     *收货地址创建或者修改
     */
    @SneakyThrows
    @Override
    public UserAddressDto CreateOrEdit(UserAddressDto input) {
        //声明一个收货地址实体
        UserAddress UserAddress=input.MapToEntity();  
        //调用数据库的增加或者修改方法
        saveOrUpdate(UserAddress);
        //把传输模型返回给前端
        return UserAddress.MapToDto();
    }
    /**
     * 收货地址删除
     */
    @Override
    public void Delete(IdInput input) {
        UserAddress entity = UserAddressMapper.selectById(input.getId());
        UserAddressMapper.deleteById(entity);
    }

    /**
     * 收货地址批量删除
     */
    @Override
    public void BatchDelete(IdsInput input) {
        for (Integer id : input.getIds()) {
            IdInput idInput = new IdInput();
            idInput.setId(id);
            Delete(idInput);
        }
    }
    /**
     * 收货地址导出
     */
    @Override
    public void Export(@RequestParam String query, HttpServletResponse response) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        UserAddressPagedInput input = mapper.readValue(query, UserAddressPagedInput.class);
        List<UserAddressDto> items =List(input).getItems();
        //声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //生成一个表格，设置表格名称为"收货地址"
        HSSFSheet sheet = workbook.createSheet("收货地址表");
        //设置表格列宽度为10个字节
        sheet.setDefaultColumnWidth(30);
        //创建标题的显示样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //创建第一行表头
        HSSFRow headrow = sheet.createRow(0);
        //表头数据
        String[] header = {"收货姓名" ,"电话" ,"省,市,区" ,"用户名称" ,"性别" ,"门牌号" ,};
        //遍历添加表头(下面模拟遍历收货地址，也是同样的操作过程)
         for (int i = 0; i < header.length; i++) {
            //创建一个单元格
            HSSFCell cell = headrow.createCell(i);
            //创建一个内容对象
            HSSFRichTextString text = new HSSFRichTextString(header[i]);
            //将内容对象的文字内容写入到单元格中
            cell.setCellValue(text);
            cell.setCellStyle(headerStyle);
        }
        for(int i=0;i<items.size();i++){
            UserAddressDto dto = items.get(i);
            //创建一行
            HSSFRow row = sheet.createRow(i + 1);    
   
              if (Extension.isNotNullOrEmpty(dto.getName())) { 
                row.createCell(0).setCellValue(new HSSFRichTextString(dto.getName()));
              }
              if (Extension.isNotNullOrEmpty(dto.getPhone())) { 
                row.createCell(1).setCellValue(new HSSFRichTextString(dto.getPhone()));
              }
              if (Extension.isNotNullOrEmpty(dto.getProviceCityArea())) {
                row.createCell(2).setCellValue(new HSSFRichTextString(dto.getProviceCityArea()));
              }

              if (dto.getBelongUserDto()!=null&&Extension.isNotNullOrEmpty(dto.getBelongUserDto().getName())) { 
                row.createCell(3).setCellValue(new HSSFRichTextString(dto.getBelongUserDto().getName()));
              }
              if (Extension.isNotNullOrEmpty(dto.getSex())) { 
                row.createCell(4).setCellValue(new HSSFRichTextString(dto.getSex()));
              }
              if (Extension.isNotNullOrEmpty(dto.getHouseNumber())) { 
                row.createCell(5).setCellValue(new HSSFRichTextString(dto.getHouseNumber()));
              }
}
        //准备将Excel的输出流通过response输出到页面下载
        //八进制输出流
        response.setContentType("application/octet-stream");
        //这后面可以设置导出Excel的名称
        response.setHeader("Content-disposition", "attachment;filename=" + System.currentTimeMillis() + ".xls");
        //刷新缓冲
        response.flushBuffer();
        //workbook将Excel写入到response的输出流中，供页面下载
        workbook.write(response.getOutputStream());
    }
}
