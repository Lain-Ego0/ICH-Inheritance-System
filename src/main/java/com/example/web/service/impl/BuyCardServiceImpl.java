package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.BuyCardDto;
import com.example.web.dto.BuyCardShopSummaryDto;
import com.example.web.dto.ShopDto;
import com.example.web.dto.query.BuyCardPagedInput;
import com.example.web.entity.AppUser;
import com.example.web.entity.BuyCard;
import com.example.web.entity.Good;
import com.example.web.entity.Shop;
import com.example.web.mapper.AppUserMapper;
import com.example.web.mapper.BuyCardMapper;
import com.example.web.mapper.GoodMapper;
import com.example.web.mapper.ShopMapper;
import com.example.web.service.BuyCardService;
import com.example.web.tools.Extension;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import com.example.web.tools.exception.CustomException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车功能实现类
 */
@Service
public class BuyCardServiceImpl extends ServiceImpl<BuyCardMapper, BuyCard> implements BuyCardService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的BuyCard表mapper对象
     */
    @Autowired
    private BuyCardMapper BuyCardMapper;
    @Autowired
    private ShopMapper ShopMapper;
    @Autowired
    private GoodMapper GoodMapper;

    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<BuyCard> BuilderQuery(BuyCardPagedInput input) {
        // 声明一个支持购物车查询的(拉姆达)表达式
        LambdaQueryWrapper<BuyCard> queryWrapper = Wrappers.<BuyCard>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, BuyCard::getId, input.getId())
                .eq(input.getCreatorId() != null, BuyCard::getCreatorId, input.getCreatorId());
        // 如果前端搜索传入查询条件则拼接查询条件

        if (input.getShopId() != null) {
            queryWrapper = queryWrapper.eq(BuyCard::getShopId, input.getShopId());
        }

        if (input.getBelongUserId() != null) {
            queryWrapper = queryWrapper.eq(BuyCard::getBelongUserId, input.getBelongUserId());
        }

        if (input.getGoodId() != null) {
            queryWrapper = queryWrapper.eq(BuyCard::getGoodId, input.getGoodId());
        }
        return queryWrapper;
    }

    /**
     * 处理购物车对于的外键数据
     */
    private List<BuyCardDto> DispatchItem(List<BuyCardDto> items)
            throws InvocationTargetException, IllegalAccessException {

        for (BuyCardDto item : items) {
            // 查询出关联的创建用户信息

            AppUser CreatorAppUserEntity = AppUserMapper
                    .selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getCreatorId())).stream()
                    .findFirst().orElse(new AppUser());
            item.setCreatorAppUserDto(CreatorAppUserEntity.MapToDto());

            // 查询出关联的Shop表信息
            Shop ShopEntity = ShopMapper.selectList(Wrappers.<Shop>lambdaQuery().eq(Shop::getId, item.getShopId()))
                    .stream().findFirst().orElse(new Shop());
            item.setShopDto(ShopEntity.MapToDto());

            // 查询出关联的Good表信息
            Good GoodEntity = GoodMapper.selectList(Wrappers.<Good>lambdaQuery().eq(Good::getId, item.getGoodId()))
                    .stream().findFirst().orElse(new Good());
            item.setGoodDto(GoodEntity.MapToDto());

            // 查询出关联的AppUser表信息
            AppUser BelongUserEntity = AppUserMapper
                    .selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getBelongUserId())).stream()
                    .findFirst().orElse(new AppUser());
            item.setBelongUserDto(BelongUserEntity.MapToDto());
        }
        return items;
    }

    /**
     * 购物车分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<BuyCardDto> List(BuyCardPagedInput input) {
        // 构建where条件+排序
        LambdaQueryWrapper<BuyCard> queryWrapper = BuilderQuery(input);

        // 按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(BuyCard::getCreationTime);
        // 构建一个分页查询的model
        Page<BuyCard> page = new Page<>(input.getPage(), input.getLimit());
        // 从数据库进行分页查询获取购物车数据
        IPage<BuyCard> pageRecords = BuyCardMapper.selectPage(page, queryWrapper);
        // 获取所有满足条件的数据行数
        Long totalCount = BuyCardMapper.selectCount(queryWrapper);
        // 把BuyCard实体转换成BuyCard传输模型
        List<BuyCardDto> items = Extension.copyBeanList(pageRecords.getRecords(), BuyCardDto.class);
        // 计算表的数据
        items = DispatchItem(items);

        // 返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 得到按店铺分组的购物车
     */
    @SneakyThrows
    @Override
    public List<BuyCardShopSummaryDto> ShopSummaryList(BuyCardPagedInput input) {

        PagedResult<BuyCardDto> list = List(input);

        List<Integer> shopIds = list.getItems().stream().map(x -> x.getShopId()).distinct().toList();

        ArrayList<BuyCardShopSummaryDto> buyCardShopSummaryDtos = new ArrayList<>();
        for (Integer shopId : shopIds) {
            var buyCardShopSummaryDto = new BuyCardShopSummaryDto();
            ShopDto shopDto = list.getItems().stream().filter(x -> x.getShopId() == shopId).map(x -> x.getShopDto())
                    .findFirst().orElse(null);
            List<BuyCardDto> buyCardDtos = list.getItems().stream().filter(x -> x.getShopId() == shopId).toList();

            buyCardShopSummaryDto.setShopDto(shopDto);
            buyCardShopSummaryDto.setBuyCardDtos(buyCardDtos);

            buyCardShopSummaryDtos.add(buyCardShopSummaryDto);
        }

        return buyCardShopSummaryDtos;

    }

    /**
     * 单个购物车查询
     */
    @SneakyThrows
    @Override
    public BuyCardDto Get(BuyCardPagedInput input) {
        if (input.getId() == null) {
            return new BuyCardDto();
        }
        PagedResult<BuyCardDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new BuyCardDto());
    }

    /**
     * 购物车创建或者修改
     */
    @SneakyThrows
    @Override
    public BuyCardDto CreateOrEdit(BuyCardDto input) {

        // 如果id是空则说明是新增
        if (Extension.isNullOrZero(input.getId())) {
            // 判断一下是否存在该文创品了
            Long count = BuyCardMapper.selectCount(Wrappers.<BuyCard>lambdaQuery()
                    .eq(BuyCard::getBelongUserId, input.getBelongUserId()).eq(BuyCard::getGoodId, input.getGoodId()));
            if (count > 0) {
                throw new CustomException("该文创品已经在购物车了,请勿重复添加");
            }
        }
        // 声明一个购物车实体
        BuyCard BuyCard = input.MapToEntity();
        // 调用数据库的增加或者修改方法
        saveOrUpdate(BuyCard);
        // 把传输模型返回给前端
        return BuyCard.MapToDto();
    }

    /**
     * 购物车删除
     */
    @Override
    public void Delete(IdInput input) {
        BuyCard entity = BuyCardMapper.selectById(input.getId());
        BuyCardMapper.deleteById(entity);
    }

    /**
     * 购物车批量删除
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
