package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.ShopDto;
import com.example.web.dto.query.ShopPagedInput;
import com.example.web.entity.AppUser;
import com.example.web.entity.Shop;
import com.example.web.enums.AuditStatusEnum;
import com.example.web.mapper.AppUserMapper;
import com.example.web.mapper.ShopMapper;
import com.example.web.service.ShopService;
import com.example.web.tools.Extension;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import com.example.web.tools.exception.CustomException;
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
 * 店铺功能实现类
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements ShopService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的Shop表mapper对象
     */
    @Autowired
    private ShopMapper ShopMapper;


    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<Shop> BuilderQuery(ShopPagedInput input) {
        //声明一个支持店铺查询的(拉姆达)表达式
        LambdaQueryWrapper<Shop> queryWrapper = Wrappers.<Shop>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, Shop::getId, input.getId())
                .eq(input.getCreatorId() != null, Shop::getCreatorId, input.getCreatorId());
        //如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getNameLike())) {
            queryWrapper = queryWrapper.like(Shop::getName, input.getNameLike());
        }
        if (Extension.isNotNullOrEmpty(input.getAddressLike())) {
            queryWrapper = queryWrapper.like(Shop::getAddress, input.getAddressLike());
        }

        if (Extension.isNotNullOrEmpty(input.getAuditReasonLike())) {
            queryWrapper = queryWrapper.like(Shop::getAuditReason, input.getAuditReasonLike());
        }
        if (Extension.isNotNullOrEmpty(input.getEnsureLike())) {
            queryWrapper = queryWrapper.like(Shop::getEnsure, input.getEnsureLike());
        }

        if (input.getUserId() != null) {
            queryWrapper = queryWrapper.eq(Shop::getUserId, input.getUserId());
        }

        if (input.getAuditStatus() != null) {
            queryWrapper = queryWrapper.eq(Shop::getAuditStatus, input.getAuditStatus());
        }

        if (input.getAuditUserId() != null) {
            queryWrapper = queryWrapper.eq(Shop::getAuditUserId, input.getAuditUserId());
        }
        if (Extension.isNotNullOrEmpty(input.getContentLike())) {
            queryWrapper = queryWrapper.like(Shop::getContent, input.getContentLike());
        }
        return queryWrapper;
    }

    /**
     * 处理店铺对于的外键数据
     */
    private List<ShopDto> DispatchItem(List<ShopDto> items) throws InvocationTargetException, IllegalAccessException {

        for (ShopDto item : items) {
            //查询出关联的创建用户信息

            AppUser CreatorAppUserEntity = AppUserMapper.selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getCreatorId())).stream().findFirst().orElse(new AppUser());
            item.setCreatorAppUserDto(CreatorAppUserEntity.MapToDto());

            //查询出关联的AppUser表信息
            AppUser UserEntity = AppUserMapper.selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getUserId())).stream().findFirst().orElse(new AppUser());
            item.setUserDto(UserEntity.MapToDto());


            //查询出关联的AppUser表信息
            AppUser AuditUserEntity = AppUserMapper.selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getAuditUserId())).stream().findFirst().orElse(new AppUser());
            item.setAuditUserDto(AuditUserEntity.MapToDto());
        }
        return items;
    }

    /**
     * 店铺分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<ShopDto> List(ShopPagedInput input) {
        //构建where条件+排序
        LambdaQueryWrapper<Shop> queryWrapper = BuilderQuery(input);

        //按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(Shop::getCreationTime);
        //构建一个分页查询的model
        Page<Shop> page = new Page<>(input.getPage(), input.getLimit());
        //从数据库进行分页查询获取店铺数据
        IPage<Shop> pageRecords = ShopMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount = ShopMapper.selectCount(queryWrapper);
        //把Shop实体转换成Shop传输模型
        List<ShopDto> items = Extension.copyBeanList(pageRecords.getRecords(), ShopDto.class);
        //计算表的数据
        items = DispatchItem(items);


        //返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个店铺查询
     */
    @SneakyThrows
    @Override
    public ShopDto Get(ShopPagedInput input) {
        if (input.getId() == null) {
            return new ShopDto();
        }
        PagedResult<ShopDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new ShopDto());
    }


    /**
     * 店铺创建或者修改
     */
    @SneakyThrows
    @Override
    public ShopDto CreateOrEdit(ShopDto input) {




        //声明一个店铺实体
        Shop Shop = input.MapToEntity();
        //调用数据库的增加或者修改方法
        saveOrUpdate(Shop);
        //把传输模型返回给前端
        return Shop.MapToDto();
    }

    /**
     * 店铺删除
     */
    @Override
    public void Delete(IdInput input) {
        Shop entity = ShopMapper.selectById(input.getId());


        AppUserMapper.delete(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, entity.getUserId()));
        ShopMapper.deleteById(entity);
    }

    /**
     * 店铺批量删除
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
     * 店铺导出
     */
    @Override
    public void Export(@RequestParam String query, HttpServletResponse response) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        ShopPagedInput input = mapper.readValue(query, ShopPagedInput.class);
        List<ShopDto> items = List(input).getItems();
        //声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //生成一个表格，设置表格名称为"店铺"
        HSSFSheet sheet = workbook.createSheet("店铺表");
        //设置表格列宽度为10个字节
        sheet.setDefaultColumnWidth(30);
        //创建标题的显示样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //创建第一行表头
        HSSFRow headrow = sheet.createRow(0);
        //表头数据
        String[] header = {"店铺名称", "Logo", "店铺地址", "用户名称", "注册邮箱", "注册电话", "审核状态", "用户名称", "审核原因", "店铺详情", "店铺保障",};
        //遍历添加表头(下面模拟遍历店铺，也是同样的操作过程)
        for (int i = 0; i < header.length; i++) {
            //创建一个单元格
            HSSFCell cell = headrow.createCell(i);
            //创建一个内容对象
            HSSFRichTextString text = new HSSFRichTextString(header[i]);
            //将内容对象的文字内容写入到单元格中
            cell.setCellValue(text);
            cell.setCellStyle(headerStyle);
        }
        for (int i = 0; i < items.size(); i++) {
            ShopDto dto = items.get(i);
            //创建一行
            HSSFRow row = sheet.createRow(i + 1);

            if (Extension.isNotNullOrEmpty(dto.getName())) {
                row.createCell(0).setCellValue(new HSSFRichTextString(dto.getName()));
            }
            if (Extension.isNotNullOrEmpty(dto.getLogoCover())) {
                row.createCell(1).setCellValue(new HSSFRichTextString(dto.getLogoCover()));
            }
            if (Extension.isNotNullOrEmpty(dto.getAddress())) {
                row.createCell(2).setCellValue(new HSSFRichTextString(dto.getAddress()));
            }
            if (dto.getUserDto() != null && Extension.isNotNullOrEmpty(dto.getUserDto().getName())) {
                row.createCell(3).setCellValue(new HSSFRichTextString(dto.getUserDto().getName()));
            }
            if (Extension.isNotNullOrEmpty(dto.getUserDto().getEmail())) {
                row.createCell(4).setCellValue(new HSSFRichTextString(dto.getUserDto().getEmail()));
            }
            if (Extension.isNotNullOrEmpty(dto.getUserDto().getPhoneNumber())) {
                row.createCell(5).setCellValue(new HSSFRichTextString(dto.getUserDto().getPhoneNumber()));
            }
            if (dto.getAuditStatus() != null) {
                row.createCell(6).setCellValue(new HSSFRichTextString(dto.getAuditStatusFormat()));
            }
            if (dto.getAuditUserDto() != null && Extension.isNotNullOrEmpty(dto.getAuditUserDto().getName())) {
                row.createCell(7).setCellValue(new HSSFRichTextString(dto.getAuditUserDto().getName()));
            }
            if (Extension.isNotNullOrEmpty(dto.getAuditReason())) {
                row.createCell(8).setCellValue(new HSSFRichTextString(dto.getAuditReason()));
            }
            if (Extension.isNotNullOrEmpty(dto.getContent())) {
                row.createCell(9).setCellValue(new HSSFRichTextString(dto.getContent()));
            }
            if (Extension.isNotNullOrEmpty(dto.getEnsure())) {
                row.createCell(10).setCellValue(new HSSFRichTextString(dto.getEnsure()));
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


    /**
     * 店铺注册
     */
    @SneakyThrows
    @Override
    public ShopDto ShopRegister(ShopDto input) {

        if (input.IsAdd()) {

            input.setAuditStatus(AuditStatusEnum.审核通过.index());
            input.setAuditReason("系统自动审核");
            //检查用户名是否存在
            Long userCount = AppUserMapper.selectCount(Wrappers.<AppUser>lambdaQuery()
                    .eq(Extension.isNotNullOrEmpty(input.getUserName()), AppUser::getUserName, input.getUserName()));
            if (userCount > 0) {
                throw new CustomException("该用户名已经存在!");
            }
            AppUser user = new AppUser();
            user.setName(input.getName());
            user.setImageUrls(input.getLogoCover());
            user.setUserName(input.getUserName());
            user.setPhoneNumber(input.getPhone());
            user.setEmail(input.getEmail());
            user.setPassword(input.getUserName());
//            user.setRoleType(RoleTypeEnum.商家.index());
            AppUserMapper.insert(user);
            input.setUserId(user.getId());
        }

        //声明一个店铺实体
        Shop Shop = input.MapToEntity();
        //调用数据库的增加或者修改方法
        saveOrUpdate(Shop);

        //把传输模型返回给前端
        return Shop.MapToDto();
    }
    /**
     * 店铺审核
     */
    @SneakyThrows
    @Override
    public ShopDto Audit(ShopDto input) {





        //声明一个店铺实体
        Shop Shop = input.MapToEntity();
        //调用数据库的增加或者修改方法
        saveOrUpdate(Shop);
        //把传输模型返回给前端
        return Shop.MapToDto();
    }

}
