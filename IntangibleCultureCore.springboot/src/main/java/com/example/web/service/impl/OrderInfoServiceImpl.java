package com.example.web.service.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.OrderCommentDto;
import com.example.web.dto.OrderDetDto;
import com.example.web.dto.OrderInfoDto;
import com.example.web.dto.query.OrderInfoPagedInput;
import com.example.web.entity.AppUser;
import com.example.web.entity.Good;
import com.example.web.entity.OrderComment;
import com.example.web.entity.OrderDet;
import com.example.web.entity.OrderInfo;
import com.example.web.entity.Shop;
import com.example.web.enums.OrderStatusEnum;
import com.example.web.mapper.AppUserMapper;
import com.example.web.mapper.BuyCardMapper;
import com.example.web.mapper.GoodMapper;
import com.example.web.mapper.GoodPropMapper;
import com.example.web.mapper.OrderCommentMapper;
import com.example.web.mapper.OrderDetMapper;
import com.example.web.mapper.OrderInfoMapper;
import com.example.web.mapper.OrderReturnMapper;
import com.example.web.mapper.ShopMapper;
import com.example.web.service.OrderInfoService;
import com.example.web.tools.Extension;
import com.example.web.tools.alipay.AliPayHelper;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import com.example.web.tools.exception.CustomException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

/**
 * 订单功能实现类
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的OrderInfo表mapper对象
     */
    @Autowired
    private OrderInfoMapper OrderInfoMapper;
    @Autowired
    private ShopMapper ShopMapper;

    @Autowired
    private OrderDetMapper OrderDetMapper;

    @Autowired
    private GoodMapper GoodMapper;

    @Autowired
    private BuyCardMapper BuyCardMapper;

    @Autowired
    private OrderCommentMapper OrderCommentMapper;
    /**
     * 操作数据库的CouponRecord表mapper对象
     */

    @Autowired
    private OrderReturnMapper OrderReturnMapper;

    @Autowired
    private GoodPropMapper GoodPropMapper;

    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<OrderInfo> BuilderQuery(OrderInfoPagedInput input) {
        // 声明一个支持订单查询的(拉姆达)表达式
        LambdaQueryWrapper<OrderInfo> queryWrapper = Wrappers.<OrderInfo>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, OrderInfo::getId, input.getId())
                .eq(input.getCreatorId() != null, OrderInfo::getCreatorId, input.getCreatorId());
        // 如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getOrderNoLike())) {
            queryWrapper = queryWrapper.like(OrderInfo::getOrderNo, input.getOrderNoLike());
        }
        if (Extension.isNotNullOrEmpty(input.getPayTypeLike())) {
            queryWrapper = queryWrapper.like(OrderInfo::getPayType, input.getPayTypeLike());
        }
        if (Extension.isNotNullOrEmpty(input.getLogisticsNoLike())) {
            queryWrapper = queryWrapper.like(OrderInfo::getLogisticsNo, input.getLogisticsNoLike());
        }
        if (Extension.isNotNullOrEmpty(input.getReceiveAddressLike())) {
            queryWrapper = queryWrapper.like(OrderInfo::getReceiveAddress, input.getReceiveAddressLike());
        }
        if (Extension.isNotNullOrEmpty(input.getReceivePhoneLike())) {
            queryWrapper = queryWrapper.like(OrderInfo::getReceivePhone, input.getReceivePhoneLike());
        }
        if (Extension.isNotNullOrEmpty(input.getReceiveNameLike())) {
            queryWrapper = queryWrapper.like(OrderInfo::getReceiveName, input.getReceiveNameLike());
        }

        if (Extension.isNotNullOrEmpty(input.getPayOrderNo())) {
            queryWrapper = queryWrapper.like(OrderInfo::getPayOrderNo, input.getPayOrderNo());
        }

        if (input.getUserId() != null) {
            queryWrapper = queryWrapper.eq(OrderInfo::getUserId, input.getUserId());
        }

        if (input.getShopUserId() != null) {
            queryWrapper = queryWrapper.eq(OrderInfo::getShopUserId, input.getShopUserId());
        }

        if (input.getShopId() != null) {
            queryWrapper = queryWrapper.eq(OrderInfo::getShopId, input.getShopId());
        }
        if (input.getPayTimeRange() != null && !input.getPayTimeRange().isEmpty()) {
            queryWrapper = queryWrapper.lt(OrderInfo::getPayTime, input.getPayTimeRange().get(1));
            queryWrapper = queryWrapper.gt(OrderInfo::getPayTime, input.getPayTimeRange().get(0));
        }
        if (input.getExpireTimeRange() != null && !input.getExpireTimeRange().isEmpty()) {
            queryWrapper = queryWrapper.lt(OrderInfo::getExpireTime, input.getExpireTimeRange().get(1));
            queryWrapper = queryWrapper.gt(OrderInfo::getExpireTime, input.getExpireTimeRange().get(0));
        }
        if (input.getOrderStatusList() != null && !input.getOrderStatusList().isEmpty()) {
            queryWrapper = queryWrapper.in(OrderInfo::getOrderStatus, input.getOrderStatusList());
        }
        return queryWrapper;
    }

    /**
     * 处理订单对于的外键数据
     */
    private List<OrderInfoDto> DispatchItem(List<OrderInfoDto> items)
            throws InvocationTargetException, IllegalAccessException {

        for (OrderInfoDto item : items) {
            // 查询出关联的创建用户信息

            AppUser CreatorAppUserEntity = AppUserMapper
                    .selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getCreatorId())).stream()
                    .findFirst().orElse(new AppUser());
            item.setCreatorAppUserDto(CreatorAppUserEntity.MapToDto());

            // 查询出关联的AppUser表信息
            AppUser ShopUserEntity = AppUserMapper
                    .selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getShopUserId())).stream()
                    .findFirst().orElse(new AppUser());
            item.setShopUserDto(ShopUserEntity.MapToDto());

            // 查询出关联的AppUser表信息
            AppUser UserEntity = AppUserMapper
                    .selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getUserId())).stream()
                    .findFirst().orElse(new AppUser());
            item.setUserDto(UserEntity.MapToDto());

            // 查询出关联的Shop表信息
            Shop ShopEntity = ShopMapper.selectList(Wrappers.<Shop>lambdaQuery().eq(Shop::getId, item.getShopId()))
                    .stream().findFirst().orElse(new Shop());
            item.setShopDto(ShopEntity.MapToDto());

            // 查询出对应的订单明细
            List<OrderDet> orderDets = OrderDetMapper
                    .selectList((Wrappers.<OrderDet>lambdaQuery().eq(OrderDet::getOrderId, item.getId())));
            List<OrderDetDto> orderDetDtos = Extension.copyBeanList(orderDets, OrderDetDto.class);
            item.setOrderDets(orderDetDtos);

            for (OrderDetDto orderDetDto : orderDetDtos) {
                // 查询出关联的Good表信息
                Good GoodEntity = GoodMapper
                        .selectList(Wrappers.<Good>lambdaQuery().eq(Good::getId, orderDetDto.getGoodId())).stream()
                        .findFirst().orElse(new Good());
                orderDetDto.setGoodDto(GoodEntity.MapToDto());
            }

            // 查询对应的订单评论
            List<OrderComment> orderComments = OrderCommentMapper
                    .selectList(Wrappers.<OrderComment>lambdaQuery().eq(OrderComment::getOrderId, item.getId()));
            List<OrderCommentDto> orderCommentDtos = Extension.copyBeanList(orderComments, OrderCommentDto.class);
            item.setOrderComments(orderCommentDtos);
        }
        return items;
    }

    /**
     * 订单分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<OrderInfoDto> List(OrderInfoPagedInput input) {
        // 构建where条件+排序
        LambdaQueryWrapper<OrderInfo> queryWrapper = BuilderQuery(input);

        // 按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(OrderInfo::getCreationTime);
        // 构建一个分页查询的model
        Page<OrderInfo> page = new Page<>(input.getPage(), input.getLimit());
        // 从数据库进行分页查询获取订单数据
        IPage<OrderInfo> pageRecords = OrderInfoMapper.selectPage(page, queryWrapper);
        // 获取所有满足条件的数据行数
        Long totalCount = OrderInfoMapper.selectCount(queryWrapper);
        // 把OrderInfo实体转换成OrderInfo传输模型
        List<OrderInfoDto> items = Extension.copyBeanList(pageRecords.getRecords(), OrderInfoDto.class);
        // 计算表的数据
        items = DispatchItem(items);

        // 返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个订单查询
     */
    @SneakyThrows
    @Override
    public OrderInfoDto Get(OrderInfoPagedInput input) {
        if (input.getId() == null) {
            return new OrderInfoDto();
        }
        PagedResult<OrderInfoDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new OrderInfoDto());
    }

    /**
     * 订单创建或者修改
     */
    @SneakyThrows
    @Override
    public OrderInfoDto CreateOrEdit(OrderInfoDto input) {
        // 声明一个订单实体
        OrderInfo OrderInfo = input.MapToEntity();
        // 调用数据库的增加或者修改方法
        saveOrUpdate(OrderInfo);
        // 把传输模型返回给前端
        return OrderInfo.MapToDto();
    }

    /**
     * 订单删除
     */
    @Override
    public void Delete(IdInput input) {
        OrderInfo entity = OrderInfoMapper.selectById(input.getId());
        OrderInfoMapper.deleteById(entity);
    }

    /**
     * 订单批量删除
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
     * 订单导出
     */
    @Override
    public void Export(@RequestParam String query, HttpServletResponse response) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        OrderInfoPagedInput input = mapper.readValue(query, OrderInfoPagedInput.class);
        List<OrderInfoDto> items = List(input).getItems();
        // 声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格，设置表格名称为"订单"
        HSSFSheet sheet = workbook.createSheet("订单表");
        // 设置表格列宽度为10个字节
        sheet.setDefaultColumnWidth(30);
        // 创建标题的显示样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 创建第一行表头
        HSSFRow headrow = sheet.createRow(0);
        // 表头数据
        String[] header = { "订单编号", "用户名称", "用户名称", "店铺店铺名称", "总数量", "总金额", "支付类型", "物流单号", "收货地址", "收货电话", "收货人", };
        // 遍历添加表头(下面模拟遍历订单，也是同样的操作过程)
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
            OrderInfoDto dto = items.get(i);
            // 创建一行
            HSSFRow row = sheet.createRow(i + 1);

            if (Extension.isNotNullOrEmpty(dto.getOrderNo())) {
                row.createCell(0).setCellValue(new HSSFRichTextString(dto.getOrderNo()));
            }
            if (dto.getUserDto() != null && Extension.isNotNullOrEmpty(dto.getUserDto().getName())) {
                row.createCell(1).setCellValue(new HSSFRichTextString(dto.getUserDto().getName()));
            }
            if (dto.getShopUserDto() != null && Extension.isNotNullOrEmpty(dto.getShopUserDto().getName())) {
                row.createCell(2).setCellValue(new HSSFRichTextString(dto.getShopUserDto().getName()));
            }
            if (dto.getShopDto() != null && Extension.isNotNullOrEmpty(dto.getShopDto().getName())) {
                row.createCell(3).setCellValue(new HSSFRichTextString(dto.getShopDto().getName()));
            }
            if (dto.getTotalQty() != null) {
                row.createCell(4).setCellValue(new HSSFRichTextString(dto.getTotalQty() + ""));
            }
            if (dto.getTotalMoney() != null) {
                // 保留2位小数
                DecimalFormat df = new DecimalFormat("#.00");
                row.createCell(5).setCellValue(new HSSFRichTextString(df.format(dto.getTotalMoney())));
            }
            if (Extension.isNotNullOrEmpty(dto.getPayType())) {
                row.createCell(6).setCellValue(new HSSFRichTextString(dto.getPayType()));
            }
            if (Extension.isNotNullOrEmpty(dto.getLogisticsNo())) {
                row.createCell(7).setCellValue(new HSSFRichTextString(dto.getLogisticsNo()));
            }
            if (Extension.isNotNullOrEmpty(dto.getReceiveAddress())) {
                row.createCell(8).setCellValue(new HSSFRichTextString(dto.getReceiveAddress()));
            }
            if (Extension.isNotNullOrEmpty(dto.getReceivePhone())) {
                row.createCell(9).setCellValue(new HSSFRichTextString(dto.getReceivePhone()));
            }
            if (Extension.isNotNullOrEmpty(dto.getReceiveName())) {
                row.createCell(10).setCellValue(new HSSFRichTextString(dto.getReceiveName()));
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
     * 下单
     */
    @Override
    public String ToOrder(List<OrderInfoDto> input) throws InvocationTargetException, IllegalAccessException {

        // 判断库存够不够
        for (OrderInfoDto orderInfoDto : input) {
            for (OrderDetDto orderDetDto : orderInfoDto.getOrderDets()) {
                Good good = GoodMapper.selectById(orderDetDto.getGoodId());
                if (good.getStock() < orderDetDto.getQty()) {
                    throw new CustomException("文创品:" + good.getName() + "库存数量不足");
                }
            }
        }

        // 内部支付订单号
        var payOrderNo = "P" + Extension.GenerateOrderNumber();

        // 循环处理订单
        for (OrderInfoDto orderInfoDto : input) {

            int totalQty = orderInfoDto.getOrderDets().stream().mapToInt(x -> x.getQty()).sum();
            double totalPrice = orderInfoDto.getOrderDets().stream().mapToDouble(x -> x.getPrice() * x.getQty()).sum();
            orderInfoDto.setOrderNo("D" + Extension.GenerateOrderNumber());
            orderInfoDto.setTotalQty(totalQty);
            orderInfoDto.setTotalMoney(totalPrice);
            orderInfoDto.setPayTime(LocalDateTime.now());
            orderInfoDto.setPayOrderNo(payOrderNo);
            if ("支付宝支付".equals(orderInfoDto.getPayType())) {
                orderInfoDto.setOrderStatus(OrderStatusEnum.待支付.index());
            } else {
                orderInfoDto.setOrderStatus(OrderStatusEnum.待发货.index());

                if (orderInfoDto.getIsSelfPickup() != null && orderInfoDto.getIsSelfPickup() == true) {
                    orderInfoDto.setOrderStatus(OrderStatusEnum.待自提.index());
                }
            }
            // 当前时间加30分钟 之内支付即可
            orderInfoDto.setExpireTime(LocalDateTime.now().plusMinutes(30));

            double couponMoney = 0;

            orderInfoDto.setOrginMoney(totalPrice);
            orderInfoDto.setDiscountMoney(couponMoney);

            orderInfoDto.setTotalMoney(totalPrice - couponMoney > 0 ? totalPrice - couponMoney : 0);

            OrderInfo orderInfo = orderInfoDto.MapToEntity();
            OrderInfoMapper.insert(orderInfo);
            AddConsumeIntegrationRecord(orderInfo.getId());
            for (OrderDetDto orderDetDto : orderInfoDto.getOrderDets()) {
                OrderDet orderDet = orderDetDto.MapToEntity();

                orderDet.setOrderId(orderInfo.getId());
                OrderDetMapper.insert(orderDet);

                Good good = GoodMapper.selectById(orderDetDto.getGoodId());
                good.setStock(good.getStock() - orderDet.getQty());
                GoodMapper.updateById(good);

                if (Extension.isNullOrZero(orderDetDto.getBuyCardId()) == false) {
                    BuyCardMapper.deleteById(orderDetDto.getBuyCardId());
                }

            }

        }

        return payOrderNo;
    }

    private void AddConsumeIntegrationRecord(Integer orderId) {

        OrderInfo orderInfo = OrderInfoMapper.selectById(orderId);

        if (orderInfo.getOrderStatus() == OrderStatusEnum.待支付.index()) {
            return;
        }

    }

    /**
     * 支付宝支付
     */
    @Override
    public String AliPay(OrderInfoDto input) throws Exception {
        OrderInfoPagedInput orderInfoPagedInput = new OrderInfoPagedInput();
        orderInfoPagedInput.setPayOrderNo(input.getPayOrderNo());
        List<OrderInfoDto> orderInfos = List(orderInfoPagedInput).getItems();

        String body = "";
        for (OrderInfoDto orderInfo : orderInfos) {

            for (OrderDetDto orderDet : orderInfo.getOrderDets()) {
                body += orderDet.getGoodDto().getName() + "*" + orderDet.getQty();
            }
        }

        var totalMoney = orderInfos.stream().mapToDouble(x -> x.getTotalMoney()).sum();

        String result = AliPayHelper.CreatePay("系统购买", body, input.getPayOrderNo(), totalMoney + "",
                input.getCallBack());

        // 把传输模型返回给前端
        return result;

    }

    /**
     * 支付宝支付检测
     */
    @Override
    public void CheckAliPayCallBack() {

        List<OrderInfo> orderInfos = OrderInfoMapper
                .selectList(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getOrderStatus, OrderStatusEnum.待支付.index())
                        .ge(OrderInfo::getExpireTime, LocalDateTime.now()));
        // 得到所有的PayOrderNo
        List<String> payOrderNos = orderInfos.stream().map(x -> x.getPayOrderNo()).distinct().toList();
        for (String payOrderNo : payOrderNos) {
            String body = AliPayHelper.QueryPayStatus(payOrderNo);
            if (body != null && body.contains("TRADE_SUCCESS")) {
                List<OrderInfo> list = orderInfos.stream().filter(x -> x.getPayOrderNo().equals(payOrderNo)).toList();
                for (OrderInfo orderInfo : list) {
                    orderInfo.setOrderStatus(OrderStatusEnum.待发货.index());
                    if (orderInfo.getIsSelfPickup() != null && orderInfo.getIsSelfPickup() == true) {
                        orderInfo.setOrderStatus(OrderStatusEnum.待自提.index());
                    }
                    OrderInfoMapper.updateById(orderInfo);
                    AddConsumeIntegrationRecord(orderInfo.getId());
                }

            }
        }
    }

    /**
     * 自动取消
     */
    @Override
    public void CheckExpire() {

        List<OrderInfo> orderInfos = OrderInfoMapper
                .selectList(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getOrderStatus, OrderStatusEnum.待支付.index())
                        .lt(OrderInfo::getExpireTime, LocalDateTime.now()));
        for (OrderInfo orderInfo : orderInfos) {
            orderInfo.setOrderStatus(OrderStatusEnum.订单关闭.index());
            OrderInfoMapper.updateById(orderInfo);

        }
    }

    /**
     * 订单发货
     */
    @Override
    public OrderInfoDto SendGood(OrderInfoDto input) throws InvocationTargetException, IllegalAccessException {
        input.setOrderStatus(OrderStatusEnum.待收货.index());
        input.setDeliveryTime(LocalDateTime.now());
        // 声明一个订单实体
        OrderInfo OrderInfo = input.MapToEntity();
        // 调用数据库的增加或者修改方法
        saveOrUpdate(OrderInfo);
        // 把传输模型返回给前端
        return OrderInfo.MapToDto();
    }

    /**
     * 到店取货
     */
    @Override
    public OrderInfoDto SelfPickup(OrderInfoDto input) throws InvocationTargetException, IllegalAccessException {
        input.setOrderStatus(OrderStatusEnum.待评价.index());
        input.setSelfPickupTime(LocalDateTime.now());
        // 声明一个订单实体
        OrderInfo OrderInfo = input.MapToEntity();
        // 调用数据库的增加或者修改方法
        saveOrUpdate(OrderInfo);
        // 把传输模型返回给前端
        return OrderInfo.MapToDto();
    }

    /**
     * 订单收货
     */
    @Override
    public OrderInfoDto GetGood(OrderInfoDto input) throws InvocationTargetException, IllegalAccessException {
        input.setOrderStatus(OrderStatusEnum.待评价.index());
        input.setGetOrderTime(LocalDateTime.now());
        // 声明一个订单实体
        OrderInfo OrderInfo = input.MapToEntity();
        // 调用数据库的增加或者修改方法
        saveOrUpdate(OrderInfo);
        // 把传输模型返回给前端
        return OrderInfo.MapToDto();
    }

    /**
     * 统计数据
     */
    @Override
    public HashMap<String, Object> GetOrderSummaryData(OrderInfoPagedInput input) {

        var result = new HashMap<String, Object>();

        List<OrderInfo> orderInfos = OrderInfoMapper
                .selectList(Wrappers.<OrderInfo>lambdaQuery());

        // 统计最近今天的订单数
        long todayOrderCount = orderInfos.stream().filter(x -> x.getPayTime().isAfter(LocalDate.now().atTime(0, 0)))
                .count();
        result.put("TodayOrderCount", todayOrderCount);

        // 统计最近7天的订单数
        long last7OrderCount = orderInfos.stream()
                .filter(x -> x.getPayTime().isAfter(LocalDate.now().plusDays(-7).atTime(0, 0))).count();
        result.put("Last7OrderCount", last7OrderCount);

        // 统计最近30天的订单数
        long last30OrderCount = orderInfos.stream()
                .filter(x -> x.getPayTime().isAfter(LocalDate.now().plusDays(-30).atTime(0, 0))).count();
        result.put("Last30OrderCount", last30OrderCount);

        // 待发货的订单数量
        long waitSendGoodOrderCount = orderInfos.stream()
                .filter(x -> x.getOrderStatus().equals(OrderStatusEnum.待发货.index())).count();
        result.put("WaitSendGoodOrderCount", waitSendGoodOrderCount);
        // 待收货的订单数量
        long waitGetGoodOrderCount = orderInfos.stream()
                .filter(x -> x.getOrderStatus().equals(OrderStatusEnum.待收货.index())).count();
        result.put("WaitGetGoodOrderCount", waitGetGoodOrderCount);
        // 待售后的订单数量
        long waitSerivceGoodOrderCount = orderInfos.stream()
                .filter(x -> x.getOrderStatus().equals(OrderStatusEnum.售后中.index())).count();
        result.put("WaitSerivceGoodOrderCount", waitSerivceGoodOrderCount);

        // 统计今日收入金额
        // 排除取消和未支付的
        var moneyOrder = orderInfos.stream().filter(x -> x.getOrderStatus() != OrderStatusEnum.已退款.index()
                && x.getOrderStatus() != OrderStatusEnum.待支付.index()).toList();

        Double todayOrderInMoneyCount = moneyOrder.stream()
                .filter(x -> x.getPayTime().isAfter(LocalDate.now().atTime(0, 0))).mapToDouble(x -> x.getTotalMoney())
                .sum();
        result.put("TodayOrderInMoneyCount", todayOrderInMoneyCount);

        // 统计最近7天收入金额
        Double last7OrderInMoneyCount = moneyOrder.stream()
                .filter(x -> x.getPayTime().isAfter(LocalDate.now().plusDays(-7).atTime(0, 0)))
                .mapToDouble(x -> x.getTotalMoney()).sum();
        result.put("Last7OrderInMoneyCount", last7OrderInMoneyCount);

        // 统计最近30天收入金额
        Double last30OrderInMoneyCount = moneyOrder.stream()
                .filter(x -> x.getPayTime().isAfter(LocalDate.now().plusDays(-30).atTime(0, 0)))
                .mapToDouble(x -> x.getTotalMoney()).sum();
        result.put("Last30OrderInMoneyCount", last30OrderInMoneyCount);

        return result;
    }

    /**
     * 得到最近的收入情况
     */
    @Override
    public HashMap<String, Object> GetOrderIncome(OrderInfoPagedInput input) {
        var result = new HashMap<String, Object>();
        List<OrderInfo> orderInfos = OrderInfoMapper
                .selectList(Wrappers.<OrderInfo>lambdaQuery());

        List<Map<String, Object>> last30dayMoneyList = new ArrayList<>();
        // 得到最近30天
        for (int i = 0; i < 30; i++) {
            LocalDate localDate = LocalDate.now().plusDays(-i);
            double sum = orderInfos.stream().filter(x -> x.getPayTime().toLocalDate().equals(localDate))
                    .mapToDouble(x -> x.getTotalMoney()).sum();

            var item = new HashMap<String, Object>();
            item.put("Date", localDate.getMonth().getValue() + "-" + localDate.getDayOfMonth());
            item.put("Value", sum);
            last30dayMoneyList.add(item);
        }
        result.put("Last30dayMoneyList", last30dayMoneyList);
        return result;
    }

    /**
     * 得到店铺热门的销量
     */
    @Override
    public List<Object> GetGoodSales(OrderInfoPagedInput input) {

        ArrayList<Object> rs = new ArrayList<>();

        List<OrderInfo> orderInfos = OrderInfoMapper
                .selectList(Wrappers.<OrderInfo>lambdaQuery()
                        .notIn(OrderInfo::getOrderStatus, OrderStatusEnum.已退款.index(), OrderStatusEnum.订单关闭.index()));

        if (orderInfos.isEmpty()) {
            return rs;
        }

        List<Integer> orderIds = orderInfos.stream().map(x -> x.getId()).toList();

        List<OrderDet> orderDets = OrderDetMapper
                .selectList(Wrappers.<OrderDet>lambdaQuery().in(OrderDet::getOrderId, orderIds));

        Map<Integer, Integer> kv = orderDets.stream()
                .collect(Collectors.toMap(OrderDet::getGoodId, OrderDet::getQty, Integer::sum));

        for (Map.Entry<Integer, Integer> integerIntegerEntry : kv.entrySet()) {
            HashMap<String, Object> stringObjectHashMap = new HashMap<>();
            Good good = GoodMapper.selectById(integerIntegerEntry.getKey());

            stringObjectHashMap.put("Good", good);
            stringObjectHashMap.put("Qty", integerIntegerEntry.getValue());

            rs.add(stringObjectHashMap);
        }

        return rs;
    }

    // 得到24小时间段改店铺购买的次数统计
    @SneakyThrows
    @Override
    public List<Object> Get24HourOrderCountEchart(OrderInfoPagedInput input) {

        ArrayList<Object> rs = new ArrayList<>();

        List<OrderInfo> orderInfos = OrderInfoMapper
                .selectList(Wrappers.<OrderInfo>lambdaQuery()
                        .notIn(OrderInfo::getOrderStatus, OrderStatusEnum.已退款.index(), OrderStatusEnum.订单关闭.index()));

        // 得到24小时时间段
        for (int i = 0; i < 24; i++) {
            LocalDateTime localDateTime = LocalDate.now().atTime(0, 0, 9).plusHours(-i);
            var item = new HashMap<String, Object>();
            // 格式改成!~2 2~3 3~4
            item.put("Date", localDateTime.getHour() + "~" + (localDateTime.getHour() + 1));

            // 得到对应的小时
            int hour = localDateTime.getHour();
            // 得到对应的小时区间订单数量
            long orderCount = orderInfos.stream().filter(x -> x.getCreationTime().getHour() >= hour
                    && x.getCreationTime().getHour() < hour + 1).count();
            item.put("Value", orderCount);
            rs.add(item);

        }
        return rs;
    }

    /**
     * 按4个季节统计订单的数量
     */
    @Override
    @SneakyThrows
    public List<Object> GetSeasonGoodSalesEchart(OrderInfoPagedInput input) {
        ArrayList<Object> rs = new ArrayList<>();
        List<OrderInfo> orderInfos = OrderInfoMapper
                .selectList(Wrappers.<OrderInfo>lambdaQuery()
                        .notIn(OrderInfo::getOrderStatus, OrderStatusEnum.已退款.index(), OrderStatusEnum.订单关闭.index()));
        // 得到4个季节 春夏秋冬
        for (int i = 0; i < 4; i++) {
            LocalDate beginDate = LocalDate.now().plusMonths(-i * 3);
            LocalDate endDate = beginDate.plusMonths(3);

            var item = new HashMap<String, Object>();
            item.put("Date", beginDate.getMonth().getValue() + "-" + endDate.getMonth().getValue());

            // 得到4季的订单记录
            long orderCount = orderInfos.stream().filter(x -> x.getPayTime().toLocalDate().isAfter(beginDate)
                    && x.getPayTime().toLocalDate().isBefore(endDate)).count();
            item.put("Value", orderCount);

            rs.add(item);
        }

        return rs;
    }
}
