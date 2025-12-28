package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.OrderReturnDto;
import com.example.web.dto.query.OrderReturnPagedInput;
import com.example.web.entity.AppUser;
import com.example.web.entity.OrderInfo;
import com.example.web.entity.OrderReturn;
import com.example.web.enums.OrderStatusEnum;
import com.example.web.mapper.AppUserMapper;
import com.example.web.mapper.OrderInfoMapper;
import com.example.web.mapper.OrderReturnMapper;
import com.example.web.service.OrderReturnService;
import com.example.web.tools.Extension;
import com.example.web.tools.alipay.AliPayHelper;
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
 * 订单退货功能实现类
 */
@Service
public class OrderReturnServiceImpl extends ServiceImpl<OrderReturnMapper, OrderReturn> implements OrderReturnService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的OrderReturn表mapper对象
     */
    @Autowired
    private OrderReturnMapper OrderReturnMapper;
    @Autowired
    private OrderInfoMapper OrderInfoMapper;


    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<OrderReturn> BuilderQuery(OrderReturnPagedInput input) {
        // 声明一个支持订单退货查询的(拉姆达)表达式
        LambdaQueryWrapper<OrderReturn> queryWrapper = Wrappers.<OrderReturn>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, OrderReturn::getId, input.getId())
                .eq(input.getCreatorId() != null, OrderReturn::getCreatorId, input.getCreatorId());
        // 如果前端搜索传入查询条件则拼接查询条件

        if (input.getOrderId() != null) {
            queryWrapper = queryWrapper.eq(OrderReturn::getOrderId, input.getOrderId());
        }

        if (input.getUserId() != null) {
            queryWrapper = queryWrapper.eq(OrderReturn::getUserId, input.getUserId());
        }

        if (input.getShopUserId() != null) {
            queryWrapper = queryWrapper.eq(OrderReturn::getShopUserId, input.getShopUserId());
        }
        if (input.getIsDiscard() != null) {
            queryWrapper = queryWrapper.eq(OrderReturn::getIsDiscard, input.getIsDiscard());
        }
        return queryWrapper;
    }

    /**
     * 处理订单退货对于的外键数据
     */
    private List<OrderReturnDto> DispatchItem(List<OrderReturnDto> items)
            throws InvocationTargetException, IllegalAccessException {

        for (OrderReturnDto item : items) {
            // 查询出关联的创建用户信息

            AppUser CreatorAppUserEntity = AppUserMapper
                    .selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getCreatorId())).stream()
                    .findFirst().orElse(new AppUser());
            item.setCreatorAppUserDto(CreatorAppUserEntity.MapToDto());

            // 查询出关联的AppUser表信息
            AppUser UserEntity = AppUserMapper
                    .selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getUserId())).stream()
                    .findFirst().orElse(new AppUser());
            item.setUserDto(UserEntity.MapToDto());

            // 查询出关联的OrderInfo表信息
            OrderInfo OrderEntity = OrderInfoMapper
                    .selectList(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getId, item.getOrderId())).stream()
                    .findFirst().orElse(new OrderInfo());
            item.setOrderDto(OrderEntity.MapToDto());

            // 查询出关联的AppUser表信息
            AppUser ShopUserEntity = AppUserMapper
                    .selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getShopUserId())).stream()
                    .findFirst().orElse(new AppUser());
            item.setShopUserDto(ShopUserEntity.MapToDto());
        }
        return items;
    }

    /**
     * 订单退货分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<OrderReturnDto> List(OrderReturnPagedInput input) {
        // 构建where条件+排序
        LambdaQueryWrapper<OrderReturn> queryWrapper = BuilderQuery(input);

        // 按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(OrderReturn::getCreationTime);
        // 构建一个分页查询的model
        Page<OrderReturn> page = new Page<>(input.getPage(), input.getLimit());
        // 从数据库进行分页查询获取订单退货数据
        IPage<OrderReturn> pageRecords = OrderReturnMapper.selectPage(page, queryWrapper);
        // 获取所有满足条件的数据行数
        Long totalCount = OrderReturnMapper.selectCount(queryWrapper);
        // 把OrderReturn实体转换成OrderReturn传输模型
        List<OrderReturnDto> items = Extension.copyBeanList(pageRecords.getRecords(), OrderReturnDto.class);
        // 计算表的数据
        items = DispatchItem(items);

        // 返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个订单退货查询
     */
    @SneakyThrows
    @Override
    public OrderReturnDto Get(OrderReturnPagedInput input) {
        if (input.getId() == null) {
            return new OrderReturnDto();
        }
        PagedResult<OrderReturnDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new OrderReturnDto());
    }

    /**
     * 订单退货创建或者修改
     */
    @SneakyThrows
    @Override
    public OrderReturnDto CreateOrEdit(OrderReturnDto input) {

        OrderInfo orderInfo = OrderInfoMapper.selectById(input.getOrderId());
        // 是新增
        if (Extension.isNullOrZero(input.getId())) {
            orderInfo.setOrderStatus(OrderStatusEnum.售后中.index());
            OrderInfoMapper.updateById(orderInfo);
        }

        // 声明一个订单退货实体
        OrderReturn OrderReturn = input.MapToEntity();
        // 调用数据库的增加或者修改方法
        saveOrUpdate(OrderReturn);
        // 把传输模型返回给前端
        return OrderReturn.MapToDto();
    }

    /**
     * 撤销申请
     */
    @SneakyThrows
    @Override
    public void Discard(OrderReturnDto input) {
        OrderInfo orderInfo = OrderInfoMapper.selectById(input.getOrderId());
        // 回退到上一个状态
        if (orderInfo.getGetOrderTime() != null) {
            orderInfo.setOrderStatus(OrderStatusEnum.待评价.index());
        } else if (orderInfo.getDeliveryTime() != null) {
            orderInfo.setOrderStatus(OrderStatusEnum.待收货.index());
        } else {
            orderInfo.setOrderStatus(OrderStatusEnum.待发货.index());
        }
        OrderInfoMapper.updateById(orderInfo);

        input.setIsDiscard(true);
        // 声明一个订单退货实体
        OrderReturn OrderReturn = input.MapToEntity();
        // 调用数据库的增加或者修改方法
        saveOrUpdate(OrderReturn);

    }

    /**
     * 商家处理
     */
    @SneakyThrows
    @Override
    public void SellerDispatch(OrderReturnDto input) {
        OrderInfo orderInfo = OrderInfoMapper.selectById(input.getOrderId());
        if (input.getIsAggren() == true) {

            if ("支付宝支付".equals(orderInfo.getPayType())) {
                AliPayHelper.CreateReturn(orderInfo.getOrderNo(), orderInfo.getPayOrderNo(),
                        orderInfo.getTotalMoney().toString());
            }
            orderInfo.setOrderStatus(OrderStatusEnum.已退款.index());
            OrderInfoMapper.updateById(orderInfo);
        }

        // 声明一个订单退货实体
        OrderReturn OrderReturn = input.MapToEntity();
        // 调用数据库的增加或者修改方法
        saveOrUpdate(OrderReturn);

    }



    /**
     * 订单退货删除
     */
    @Override
    public void Delete(IdInput input) {
        OrderReturn entity = OrderReturnMapper.selectById(input.getId());
        OrderReturnMapper.deleteById(entity);
    }

    /**
     * 订单退货批量删除
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
     * 订单退货导出
     */
    @Override
    public void Export(@RequestParam String query, HttpServletResponse response) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        OrderReturnPagedInput input = mapper.readValue(query, OrderReturnPagedInput.class);
        List<OrderReturnDto> items = List(input).getItems();
        // 声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格，设置表格名称为"订单退货"
        HSSFSheet sheet = workbook.createSheet("订单退货表");
        // 设置表格列宽度为10个字节
        sheet.setDefaultColumnWidth(30);
        // 创建标题的显示样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 创建第一行表头
        HSSFRow headrow = sheet.createRow(0);
        // 表头数据
        String[] header = { "订单订单编号", "用户名称", "用户名称", "申请原因", "申请退款金额", };
        // 遍历添加表头(下面模拟遍历订单退货，也是同样的操作过程)
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
            OrderReturnDto dto = items.get(i);
            // 创建一行
            HSSFRow row = sheet.createRow(i + 1);

            if (dto.getOrderDto() != null && Extension.isNotNullOrEmpty(dto.getOrderDto().getOrderNo())) {
                row.createCell(0).setCellValue(new HSSFRichTextString(dto.getOrderDto().getOrderNo()));
            }
            if (dto.getUserDto() != null && Extension.isNotNullOrEmpty(dto.getUserDto().getName())) {
                row.createCell(1).setCellValue(new HSSFRichTextString(dto.getUserDto().getName()));
            }
            if (dto.getShopUserDto() != null && Extension.isNotNullOrEmpty(dto.getShopUserDto().getName())) {
                row.createCell(2).setCellValue(new HSSFRichTextString(dto.getShopUserDto().getName()));
            }
            if (dto.getApplyReason() != null) {
                row.createCell(3).setCellValue(new HSSFRichTextString(dto.getApplyReason() + ""));
            }
            if (dto.getReturnMoney() != null) {
                // 保留2位小数
                DecimalFormat df = new DecimalFormat("#.00");
                row.createCell(4).setCellValue(new HSSFRichTextString(df.format(dto.getReturnMoney())));
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
