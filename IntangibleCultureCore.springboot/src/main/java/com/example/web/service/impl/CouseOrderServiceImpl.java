package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.AppUserDto;
import com.example.web.dto.CouseDto;
import com.example.web.dto.CouseOrderDto;
import com.example.web.dto.query.CouseOrderPagedInput;
import com.example.web.entity.AppUser;
import com.example.web.entity.Couse;
import com.example.web.entity.CouseOrder;
import com.example.web.enums.CouseOrderStatusEnum;
import com.example.web.mapper.AppUserMapper;
import com.example.web.mapper.CouseMapper;
import com.example.web.mapper.CouseOrderMapper;
import com.example.web.service.CouseOrderService;
import com.example.web.tools.Extension;
import com.example.web.tools.alipay.AliPayHelper;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import com.example.web.tools.exception.CustomException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 课程订单功能实现类
 */
@Service
public class CouseOrderServiceImpl extends ServiceImpl<CouseOrderMapper, CouseOrder> implements CouseOrderService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的CouseOrder表mapper对象
     */
    @Autowired
    private CouseOrderMapper CouseOrderMapper;
    @Autowired
    private CouseMapper CouseMapper;

    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<CouseOrder> BuilderQuery(CouseOrderPagedInput input) {
        // 声明一个支持课程订单查询的(拉姆达)表达式
        LambdaQueryWrapper<CouseOrder> queryWrapper = Wrappers.<CouseOrder>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, CouseOrder::getId, input.getId())
                .eq(input.getCreatorId() != null, CouseOrder::getCreatorId, input.getCreatorId());
        // 如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getNo())) {
            queryWrapper = queryWrapper.like(CouseOrder::getNo, input.getNo());
        }
        if (Extension.isNotNullOrEmpty(input.getPayType())) {
            queryWrapper = queryWrapper.like(CouseOrder::getPayType, input.getPayType());
        }

        if (input.getCouseId() != null) {
            queryWrapper = queryWrapper.eq(CouseOrder::getCouseId, input.getCouseId());
        }

        if (input.getUserId() != null) {
            queryWrapper = queryWrapper.eq(CouseOrder::getUserId, input.getUserId());
        }
        if (input.getReturnTimeRange() != null && !input.getReturnTimeRange().isEmpty()) {
            queryWrapper = queryWrapper.lt(CouseOrder::getReturnTime, input.getReturnTimeRange().get(1));
            queryWrapper = queryWrapper.gt(CouseOrder::getReturnTime, input.getReturnTimeRange().get(0));
        }
        if (input.getIsReturn() != null) {
            queryWrapper = queryWrapper.eq(CouseOrder::getIsReturn, input.getIsReturn());
        }

        if (input.getTeachUserId() != null) {
            // 查询对应的课程
            List<Couse> couseList = CouseMapper
                    .selectList(Wrappers.<Couse>lambdaQuery().eq(Couse::getCreatorId, input.getTeachUserId()));
            if (couseList.size() > 0) {
                queryWrapper = queryWrapper.in(CouseOrder::getCouseId,
                        couseList.stream().map(Couse::getId).collect(Collectors.toList()));
            } else {
                queryWrapper = queryWrapper.eq(CouseOrder::getCouseId, 0);
            }
        }
        return queryWrapper;
    }

    /**
     * 处理课程订单对于的外键数据
     */
    private List<CouseOrderDto> DispatchItem(List<CouseOrderDto> items)
            throws InvocationTargetException, IllegalAccessException {

        for (CouseOrderDto item : items) {

            // 查询创建人
            AppUser CreateUserEntity = AppUserMapper
                    .selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getCreatorId())).stream()
                    .findFirst().orElse(new AppUser());
            item.setCreatorAppUserDto(CreateUserEntity.MapToDto());

            // 查询出关联的AppUser表信息
            AppUser UserEntity = AppUserMapper.selectById(item.getUserId());
            item.setUserDto(UserEntity != null ? UserEntity.MapToDto() : new AppUserDto());

            // 查询出关联的Couse表信息
            Couse CouseEntity = CouseMapper.selectById(item.getCouseId());
            item.setCouseDto(CouseEntity != null ? CouseEntity.MapToDto() : new CouseDto());
        }

        return items;
    }

    /**
     * 课程订单分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<CouseOrderDto> List(CouseOrderPagedInput input) {
        // 构建where条件+排序
        LambdaQueryWrapper<CouseOrder> queryWrapper = BuilderQuery(input);

        // 按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(CouseOrder::getCreationTime);
        // 构建一个分页查询的model
        Page<CouseOrder> page = new Page<>(input.getPage(), input.getLimit());
        // 从数据库进行分页查询获取课程订单数据
        IPage<CouseOrder> pageRecords = CouseOrderMapper.selectPage(page, queryWrapper);
        // 获取所有满足条件的数据行数
        Long totalCount = CouseOrderMapper.selectCount(queryWrapper);
        // 把CouseOrder实体转换成CouseOrder传输模型
        List<CouseOrderDto> items = Extension.copyBeanList(pageRecords.getRecords(), CouseOrderDto.class);

        DispatchItem(items);
        // 返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个课程订单查询
     */
    @SneakyThrows
    @Override
    public CouseOrderDto Get(CouseOrderPagedInput input) {
        if (input.getId() == null) {
            return new CouseOrderDto();
        }

        PagedResult<CouseOrderDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new CouseOrderDto());
    }

    /**
     * 课程订单创建或者修改
     */
    @SneakyThrows
    @Override
    public CouseOrderDto CreateOrEdit(CouseOrderDto input) {
        // 声明一个课程订单实体
        CouseOrder CouseOrder = input.MapToEntity();
        // 调用数据库的增加或者修改方法
        saveOrUpdate(CouseOrder);
        // 把传输模型返回给前端
        return CouseOrder.MapToDto();
    }

    /**
     * 课程订单删除
     */
    @Override
    public void Delete(IdInput input) {
        CouseOrder entity = CouseOrderMapper.selectById(input.getId());
        CouseOrderMapper.deleteById(entity);
    }

    /**
     * 课程订单批量删除
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
     * 报名
     */
    @SneakyThrows
    @Override
    public CouseOrderDto Enroll(CouseOrderDto input) {

        input.setNo("D" + Extension.GenerateOrderNumber());
        if ("支付宝支付".equals(input.getPayType())) {
            input.setOrderStatus(CouseOrderStatusEnum.待支付.index());
        } else {
            input.setOrderStatus(CouseOrderStatusEnum.已支付.index());
        }
        CouseOrder CouseOrder = input.MapToEntity();
        saveOrUpdate(CouseOrder);
        return CouseOrder.MapToDto();
    }

    /**
     * 支付宝支付
     */
    @Override
    @SneakyThrows
    public String AliPay(CouseOrderDto input) {
        // 根据no查询预约记录
        CouseOrder couseOrder = CouseOrderMapper.selectOne(Wrappers.<CouseOrder>lambdaQuery()
                .eq(CouseOrder::getNo, input.getNo()));
        // 课程
        Couse couse = CouseMapper.selectById(couseOrder.getCouseId());

        String body = "购买了" + couse.getName() + "课程";

        var totalMoney = couseOrder.getPayMoney();

        String result = AliPayHelper.CreatePay("系统购买", body, couseOrder.getNo(), totalMoney + "",
                input.getCallBack());

        // 把传输模型返回给前端
        return result;

    }

    /**
     * 取消课程
     */
    @SneakyThrows
    @Override
    public void Cancel(CouseOrderDto input) {

        // 在购买1天内可以取消
        if (LocalDateTime.now().isAfter(input.getCreationTime().plusDays(1))) {
            throw new CustomException("该课程超过1天不能取消");
        }

        input.setOrderStatus(CouseOrderStatusEnum.已退款.index());
        input.setIsReturn(true);
        input.setReturnTime(LocalDateTime.now());

        CouseOrder CouseOrder = input.MapToEntity();
        CouseOrderMapper.updateById(CouseOrder);
    }

    /**
     * 支付宝支付检测
     */
    @Override
    public void CheckAliPayCallBack() {

        List<CouseOrder> orderInfos = CouseOrderMapper
                .selectList(Wrappers.<CouseOrder>lambdaQuery()
                        .isNull(CouseOrder::getPayTime)
                        .eq(CouseOrder::getPayType, "支付宝支付"));

        // 得到所有的PayOrderNo
        List<String> payOrderNos = orderInfos.stream().map(x -> x.getNo()).distinct().toList();
        for (String payOrderNo : payOrderNos) {
            String body = AliPayHelper.QueryPayStatus(payOrderNo);
            if (body != null && body.contains("TRADE_SUCCESS")) {
                List<CouseOrder> list = orderInfos.stream().filter(x -> x.getNo().equals(payOrderNo)).toList();
                for (CouseOrder couseOrder : list) {
                    couseOrder.setPayTime(LocalDateTime.now());
                    couseOrder.setOrderStatus(CouseOrderStatusEnum.已支付.index());
                }
                CouseOrderMapper.updateById(list);
            } else {
                // 查询
                CouseOrder couseOrder = CouseOrderMapper.selectOne(Wrappers.<CouseOrder>lambdaQuery()
                        .eq(CouseOrder::getNo, payOrderNo));
                // 如果超过了5分钟
                if (couseOrder != null
                        && LocalDateTime.now().isAfter(couseOrder.getCreationTime().plusMinutes(5))) {
                    couseOrder.setPayTime(LocalDateTime.now());
                    couseOrder.setOrderStatus(CouseOrderStatusEnum.订单关闭.index());
                    saveOrUpdate(couseOrder);
                }
            }
        }
    }

}
