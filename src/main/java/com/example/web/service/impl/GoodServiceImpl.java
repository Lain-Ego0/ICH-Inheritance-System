package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.GoodDto;
import com.example.web.dto.GoodPropDto;
import com.example.web.dto.query.GoodPagedInput;
import com.example.web.entity.*;
import com.example.web.mapper.*;
import com.example.web.service.GoodService;
import com.example.web.tools.BaseContext;
import com.example.web.tools.CollaborativeFiltering;
import com.example.web.tools.Extension;
import com.example.web.tools.dto.*;
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
import java.text.DecimalFormat;
import java.util.List;

/**
 * 文创品功能实现类
 */
@Service
public class GoodServiceImpl extends ServiceImpl<GoodMapper, Good> implements GoodService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的Good表mapper对象
     */
    @Autowired
    private GoodMapper GoodMapper;
    @Autowired
    private GoodTypeMapper GoodTypeMapper;
    @Autowired
    private ShopMapper ShopMapper;

    @Autowired
    private OrderDetMapper OrderDetMapper;

    /**
     * 操作数据库的GoodProp表mapper对象
     */
    @Autowired
    private GoodPropMapper GoodPropMapper;

    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<Good> BuilderQuery(GoodPagedInput input) {
        // 声明一个支持文创品查询的(拉姆达)表达式
        LambdaQueryWrapper<Good> queryWrapper = Wrappers.<Good>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, Good::getId, input.getId())
                .eq(input.getCreatorId() != null, Good::getCreatorId, input.getCreatorId());
        // 如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getNameLike())) {
            queryWrapper = queryWrapper.like(Good::getName, input.getNameLike());
        }
        if (Extension.isNotNullOrEmpty(input.getUnitLike())) {
            queryWrapper = queryWrapper.like(Good::getUnit, input.getUnitLike());
        }

        if (input.getShopId() != null) {
            queryWrapper = queryWrapper.eq(Good::getShopId, input.getShopId());
        }

        if (input.getGoodTypeId() != null) {
            queryWrapper = queryWrapper.eq(Good::getGoodTypeId, input.getGoodTypeId());
        }

        if (input.getGoodIds() != null && input.getGoodIds().isEmpty() == false) {
            queryWrapper = queryWrapper.in(Good::getId, input.getGoodIds());
        }

        if (Extension.isNotNullOrEmpty(input.getContentLike())) {
            queryWrapper = queryWrapper.like(Good::getContent, input.getContentLike());
        }

        if (input.getIsReplenishment() != null) {
            queryWrapper = queryWrapper.apply("Stock<ReplenishmentLimit ");
        }

        if (Extension.isNotNullOrEmpty(input.getKeyWord())) {
            queryWrapper = queryWrapper.like(Good::getName, input.getKeyWord())
                    .or().like(Good::getContent, input.getKeyWord());
        }

        return queryWrapper;
    }

    /**
     * 处理文创品对于的外键数据
     */
    private List<GoodDto> DispatchItem(List<GoodDto> items) throws InvocationTargetException, IllegalAccessException {

        for (GoodDto item : items) {
            // 查询出关联的创建用户信息

            AppUser CreatorAppUserEntity = AppUserMapper
                    .selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getCreatorId())).stream()
                    .findFirst().orElse(new AppUser());
            item.setCreatorAppUserDto(CreatorAppUserEntity.MapToDto());

            // 查询出关联的GoodType表信息
            GoodType GoodTypeEntity = GoodTypeMapper
                    .selectList(Wrappers.<GoodType>lambdaQuery().eq(GoodType::getId, item.getGoodTypeId())).stream()
                    .findFirst().orElse(new GoodType());
            item.setGoodTypeDto(GoodTypeEntity.MapToDto());

            // 查询出关联的Shop表信息
            Shop ShopEntity = ShopMapper.selectList(Wrappers.<Shop>lambdaQuery().eq(Shop::getId, item.getShopId()))
                    .stream().findFirst().orElse(new Shop());
            item.setShopDto(ShopEntity.MapToDto());

            // 统计该文创品购买人次
            long buyCount = OrderDetMapper
                    .selectList(Wrappers.<OrderDet>lambdaQuery().eq(OrderDet::getGoodId, item.getId())).stream()
                    .map(x -> x.getCreatorId()).distinct().count();
            item.setBuyCount((int) buyCount);

            // 查询出关联的GoodProp表信息
            List<GoodProp> GoodPropEntity = GoodPropMapper
                    .selectList(Wrappers.<GoodProp>lambdaQuery().eq(GoodProp::getGoodId, item.getId()));
            item.setGoodPropDtoList(Extension.copyBeanList(GoodPropEntity, GoodPropDto.class));
        }
        return items;
    }

    /**
     * 协同过滤推荐
     */
    @SneakyThrows
    @Override
    public PagedResult<GoodDto> GetRecommendList(GoodPagedInput input) {
        CurrentUserDto currentUserDto = BaseContext.getCurrentUserDto();

        if (currentUserDto != null && currentUserDto.getUserId() > 0) {
            Integer userId = currentUserDto.getUserId();
            CollaborativeFiltering collaborativeFiltering = new CollaborativeFiltering();
            // 查询所有订单明细
            List<OrderDet> orderDets = OrderDetMapper.selectList(null);
            for (OrderDet orderDet : orderDets) {
                // 每个文创品购买次数算一个点数
                collaborativeFiltering.AddUserRating(orderDet.getCreatorId(), orderDet.getGoodId(), 1.00);
            }
            List<UserCalculateCosineSimilarity> userCalculateCosineSimilarityList = collaborativeFiltering
                    .GetUserCalculateCosineSimilarity(userId);

            // 查询对应的文创品

            List<Integer> goodIds = userCalculateCosineSimilarityList.stream().flatMap(x -> x.getItemIds().stream())
                    .toList();
            if (goodIds.size() == 0) {
                return PagedResult.GetEmptyInstance();
            }

            input.setGoodIds(goodIds);

            return List(input);

        }
        return PagedResult.GetEmptyInstance();
    }

    /**
     * 文创品分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<GoodDto> List(GoodPagedInput input) {
        // 构建where条件+排序
        LambdaQueryWrapper<Good> queryWrapper = BuilderQuery(input);

        // 按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(Good::getCreationTime);
        // 构建一个分页查询的model
        Page<Good> page = new Page<>(input.getPage(), input.getLimit());
        // 从数据库进行分页查询获取文创品数据
        IPage<Good> pageRecords = GoodMapper.selectPage(page, queryWrapper);
        // 获取所有满足条件的数据行数
        Long totalCount = GoodMapper.selectCount(queryWrapper);
        // 把Good实体转换成Good传输模型
        List<GoodDto> items = Extension.copyBeanList(pageRecords.getRecords(), GoodDto.class);
        // 计算表的数据
        items = DispatchItem(items);

        // 返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个文创品查询
     */
    @SneakyThrows
    @Override
    public GoodDto Get(GoodPagedInput input) {
        if (input.getId() == null) {
            return new GoodDto();
        }
        PagedResult<GoodDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new GoodDto());
    }

    /**
     * 文创品创建或者修改
     */
    @SneakyThrows
    @Override
    public GoodDto CreateOrEdit(GoodDto input) {

        // 删除所有的属性
        GoodPropMapper.delete(Wrappers.<GoodProp>lambdaQuery().eq(GoodProp::getGoodId, input.getId()));

        // 声明一个文创品实体
        Good Good = input.MapToEntity();
        // 调用数据库的增加或者修改方法
        saveOrUpdate(Good);
        // 把属性添加到数据库
        for (GoodPropDto goodPropDto : input.getGoodPropDtoList()) {
            goodPropDto.setGoodId(Good.getId());
            GoodPropMapper.insert(goodPropDto.MapToEntity());
        }
        // 把传输模型返回给前端
        return Good.MapToDto();
    }

    /**
     * 文创品删除
     */
    @Override
    public void Delete(IdInput input) {
        Good entity = GoodMapper.selectById(input.getId());
        // 删除所有属性
        GoodPropMapper.delete(Wrappers.<GoodProp>lambdaQuery().eq(GoodProp::getGoodId, entity.getId()));
        GoodMapper.deleteById(entity);
    }

    /**
     * 文创品批量删除
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
     * 文创品导出
     */
    @Override
    public void Export(@RequestParam String query, HttpServletResponse response) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        GoodPagedInput input = mapper.readValue(query, GoodPagedInput.class);
        List<GoodDto> items = List(input).getItems();
        // 声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格，设置表格名称为"文创品"
        HSSFSheet sheet = workbook.createSheet("文创品表");
        // 设置表格列宽度为10个字节
        sheet.setDefaultColumnWidth(30);
        // 创建标题的显示样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 创建第一行表头
        HSSFRow headrow = sheet.createRow(0);
        // 表头数据
        String[] header = { "名称", "封面", "主图", "价格", "库存", "详情内容", "店铺名称", "单位", "文创品分类名称", };
        // 遍历添加表头(下面模拟遍历文创品，也是同样的操作过程)
        for (int i = 0; i < header.length; i++) {
            // 创建一个单元格
            HSSFCell cell = headrow.createCell(i);
            // 创建一个内容对象
            HSSFRichTextString text = new HSSFRichTextString(header[i]);
            // 将内容对象的文字内容写入到单元格中
            cell.setCellValue(text);
            cell.setCellStyle(headerStyle);
        }
        for (int i = 0; i < items.size(); i++) {
            GoodDto dto = items.get(i);
            // 创建一行
            HSSFRow row = sheet.createRow(i + 1);

            if (Extension.isNotNullOrEmpty(dto.getName())) {
                row.createCell(0).setCellValue(new HSSFRichTextString(dto.getName()));
            }
            if (Extension.isNotNullOrEmpty(dto.getCover())) {
                row.createCell(1).setCellValue(new HSSFRichTextString(dto.getCover()));
            }
            if (Extension.isNotNullOrEmpty(dto.getImageUrls())) {
                row.createCell(2).setCellValue(new HSSFRichTextString(dto.getImageUrls()));
            }
            if (dto.getPrice() != null) {
                // 保留2位小数
                DecimalFormat df = new DecimalFormat("#.00");
                row.createCell(3).setCellValue(new HSSFRichTextString(df.format(dto.getPrice())));
            }
            if (dto.getStock() != null) {
                // 保留2位小数
                DecimalFormat df = new DecimalFormat("#.00");
                row.createCell(4).setCellValue(new HSSFRichTextString(df.format(dto.getStock())));
            }
            if (Extension.isNotNullOrEmpty(dto.getContent())) {
                row.createCell(5).setCellValue(new HSSFRichTextString(dto.getContent()));
            }
            if (dto.getShopDto() != null && Extension.isNotNullOrEmpty(dto.getShopDto().getName())) {
                row.createCell(6).setCellValue(new HSSFRichTextString(dto.getShopDto().getName()));
            }

            if (Extension.isNotNullOrEmpty(dto.getUnit())) {
                row.createCell(7).setCellValue(new HSSFRichTextString(dto.getUnit()));
            }

            if (dto.getGoodTypeDto() != null && Extension.isNotNullOrEmpty(dto.getGoodTypeDto().getName())) {
                row.createCell(8).setCellValue(new HSSFRichTextString(dto.getGoodTypeDto().getName()));
            }
        }
        // 准备将Excel的输出流通过response输出到页面下载
        // 八进制输出流
        response.setContentType("application/octet-stream");
        // 这后面可以设置导出Excel的名称
        response.setHeader("Content-disposition", "attachment;filename=" + System.currentTimeMillis() + ".xls");
        // 刷新缓冲
        response.flushBuffer();
        // workbook将Excel写入到response的输出流中，供页面下载
        workbook.write(response.getOutputStream());
    }
}
