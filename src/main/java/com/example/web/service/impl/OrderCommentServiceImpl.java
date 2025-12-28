package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.OrderCommentDto;
import com.example.web.dto.query.OrderCommentPagedInput;
import com.example.web.entity.*;
import com.example.web.enums.OrderStatusEnum;
import com.example.web.mapper.*;
import com.example.web.service.OrderCommentService;
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
import java.text.DecimalFormat;
import java.util.List;

/**
 * 订单评价功能实现类
 */
@Service
public class OrderCommentServiceImpl extends ServiceImpl<OrderCommentMapper, OrderComment>
        implements OrderCommentService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的OrderComment表mapper对象
     */
    @Autowired
    private OrderCommentMapper OrderCommentMapper;
    @Autowired
    private ShopMapper ShopMapper;
    @Autowired
    private OrderInfoMapper OrderInfoMapper;
    @Autowired
    private GoodMapper GoodMapper;

    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<OrderComment> BuilderQuery(OrderCommentPagedInput input) {
        // 声明一个支持订单评价查询的(拉姆达)表达式
        LambdaQueryWrapper<OrderComment> queryWrapper = Wrappers.<OrderComment>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, OrderComment::getId, input.getId())
                .eq(input.getCreatorId() != null, OrderComment::getCreatorId, input.getCreatorId());
        // 如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getCommentLike())) {
            queryWrapper = queryWrapper.like(OrderComment::getComment, input.getCommentLike());
        }

        if (input.getGoodId() != null) {
            queryWrapper = queryWrapper.eq(OrderComment::getGoodId, input.getGoodId());
        }

        if (input.getOrderId() != null) {
            queryWrapper = queryWrapper.eq(OrderComment::getOrderId, input.getOrderId());
        }

        if (input.getShopId() != null) {
            queryWrapper = queryWrapper.eq(OrderComment::getShopId, input.getShopId());
        }

        if (input.getCommentUserId() != null) {
            queryWrapper = queryWrapper.eq(OrderComment::getCommentUserId, input.getCommentUserId());
        }
        return queryWrapper;
    }

    /**
     * 处理订单评价对于的外键数据
     */
    private List<OrderCommentDto> DispatchItem(List<OrderCommentDto> items)
            throws InvocationTargetException, IllegalAccessException {

        for (OrderCommentDto item : items) {
            // 查询出关联的创建用户信息

            AppUser CreatorAppUserEntity = AppUserMapper
                    .selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getCreatorId())).stream()
                    .findFirst().orElse(new AppUser());
            item.setCreatorAppUserDto(CreatorAppUserEntity.MapToDto());

            // 查询出关联的Shop表信息
            Shop ShopEntity = ShopMapper.selectList(Wrappers.<Shop>lambdaQuery().eq(Shop::getId, item.getShopId()))
                    .stream().findFirst().orElse(new Shop());
            item.setShopDto(ShopEntity.MapToDto());

            // 查询出关联的AppUser表信息
            AppUser CommentUserEntity = AppUserMapper
                    .selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getCommentUserId())).stream()
                    .findFirst().orElse(new AppUser());
            item.setCommentUserDto(CommentUserEntity.MapToDto());

            // 查询出关联的OrderInfo表信息
            OrderInfo OrderEntity = OrderInfoMapper
                    .selectList(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getId, item.getOrderId())).stream()
                    .findFirst().orElse(new OrderInfo());
            item.setOrderDto(OrderEntity.MapToDto());

            // 查询出关联的Good表信息
            Good GoodEntity = GoodMapper.selectList(Wrappers.<Good>lambdaQuery().eq(Good::getId, item.getGoodId()))
                    .stream().findFirst().orElse(new Good());
            item.setGoodDto(GoodEntity.MapToDto());
        }
        return items;
    }

    /**
     * 订单评价分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<OrderCommentDto> List(OrderCommentPagedInput input) {
        // 构建where条件+排序
        LambdaQueryWrapper<OrderComment> queryWrapper = BuilderQuery(input);

        // 按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(OrderComment::getCreationTime);
        // 构建一个分页查询的model
        Page<OrderComment> page = new Page<>(input.getPage(), input.getLimit());
        // 从数据库进行分页查询获取订单评价数据
        IPage<OrderComment> pageRecords = OrderCommentMapper.selectPage(page, queryWrapper);
        // 获取所有满足条件的数据行数
        Long totalCount = OrderCommentMapper.selectCount(queryWrapper);
        // 把OrderComment实体转换成OrderComment传输模型
        List<OrderCommentDto> items = Extension.copyBeanList(pageRecords.getRecords(), OrderCommentDto.class);
        // 计算表的数据
        items = DispatchItem(items);

        // 返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个订单评价查询
     */
    @SneakyThrows
    @Override
    public OrderCommentDto Get(OrderCommentPagedInput input) {
        if (input.getId() == null) {
            return new OrderCommentDto();
        }
        PagedResult<OrderCommentDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new OrderCommentDto());
    }

    /**
     * 订单评价创建或者修改
     */
    @SneakyThrows
    @Override
    public OrderCommentDto CreateOrEdit(OrderCommentDto input) {
        // 声明一个订单评价实体
        OrderComment OrderComment = input.MapToEntity();
        // 调用数据库的增加或者修改方法
        saveOrUpdate(OrderComment);
        // 把传输模型返回给前端
        return OrderComment.MapToDto();
    }

    /**
     * 订单评价删除
     */
    @Override
    public void Delete(IdInput input) {
        OrderComment entity = OrderCommentMapper.selectById(input.getId());
        OrderCommentMapper.deleteById(entity);
    }

    /**
     * 订单评价批量删除
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
     * 订单评价导出
     */
    @Override
    public void Export(@RequestParam String query, HttpServletResponse response) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        OrderCommentPagedInput input = mapper.readValue(query, OrderCommentPagedInput.class);
        List<OrderCommentDto> items = List(input).getItems();
        // 声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格，设置表格名称为"订单评价"
        HSSFSheet sheet = workbook.createSheet("订单评价表");
        // 设置表格列宽度为10个字节
        sheet.setDefaultColumnWidth(30);
        // 创建标题的显示样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 创建第一行表头
        HSSFRow headrow = sheet.createRow(0);
        // 表头数据
        String[] header = { "文创品名称", "订单订单编号", "评分", "评论", "店铺店铺名称", "图片", "用户名称", };
        // 遍历添加表头(下面模拟遍历订单评价，也是同样的操作过程)
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
            OrderCommentDto dto = items.get(i);
            // 创建一行
            HSSFRow row = sheet.createRow(i + 1);

            if (dto.getGoodDto() != null && Extension.isNotNullOrEmpty(dto.getGoodDto().getName())) {
                row.createCell(0).setCellValue(new HSSFRichTextString(dto.getGoodDto().getName()));
            }
            if (dto.getOrderDto() != null && Extension.isNotNullOrEmpty(dto.getOrderDto().getOrderNo())) {
                row.createCell(1).setCellValue(new HSSFRichTextString(dto.getOrderDto().getOrderNo()));
            }
            if (dto.getCommentScore() != null) {
                // 保留2位小数
                DecimalFormat df = new DecimalFormat("#.00");
                row.createCell(2).setCellValue(new HSSFRichTextString(df.format(dto.getCommentScore())));
            }
            if (Extension.isNotNullOrEmpty(dto.getComment())) {
                row.createCell(3).setCellValue(new HSSFRichTextString(dto.getComment()));
            }
            if (dto.getShopDto() != null && Extension.isNotNullOrEmpty(dto.getShopDto().getName())) {
                row.createCell(4).setCellValue(new HSSFRichTextString(dto.getShopDto().getName()));
            }
            if (Extension.isNotNullOrEmpty(dto.getImageUrls())) {
                row.createCell(5).setCellValue(new HSSFRichTextString(dto.getImageUrls()));
            }
            if (dto.getCommentUserDto() != null && Extension.isNotNullOrEmpty(dto.getCommentUserDto().getName())) {
                row.createCell(6).setCellValue(new HSSFRichTextString(dto.getCommentUserDto().getName()));
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

    /**
     * 订单评价
     */
    @Override
    public void OrderComment(List<OrderCommentDto> input) throws InvocationTargetException, IllegalAccessException {

        Integer orderId = input.get(0).getOrderId();
        OrderInfo orderInfo = OrderInfoMapper.selectById(orderId);
        // 查询对应订单
        orderInfo.setOrderStatus(OrderStatusEnum.完成.index());

        OrderInfoMapper.updateById(orderInfo);

        List<OrderComment> orderComments = Extension.copyBeanList(input, OrderComment.class);

        saveBatch(orderComments);
    }

}
