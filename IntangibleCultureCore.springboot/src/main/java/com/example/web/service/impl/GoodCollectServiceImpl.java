package com.example.web.service.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

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
import com.example.web.dto.GoodCollectDto;
import com.example.web.dto.query.GoodCollectPagedInput;
import com.example.web.entity.AppUser;
import com.example.web.entity.Good;
import com.example.web.entity.GoodCollect;
import com.example.web.mapper.AppUserMapper;
import com.example.web.mapper.GoodCollectMapper;
import com.example.web.mapper.GoodMapper;
import com.example.web.service.GoodCollectService;
import com.example.web.tools.Extension;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

/**
 * 文创品收藏功能实现类
 */
@Service
public class GoodCollectServiceImpl extends ServiceImpl<GoodCollectMapper, GoodCollect> implements GoodCollectService {

  /**
   * 操作数据库AppUser表mapper对象
   */
  @Autowired
  private AppUserMapper AppUserMapper;
  /**
   * 操作数据库的GoodCollect表mapper对象
   */
  @Autowired
  private GoodCollectMapper GoodCollectMapper;
  @Autowired
  private GoodMapper GoodMapper;

  /**
   * 构建表查询sql
   */
  private LambdaQueryWrapper<GoodCollect> BuilderQuery(GoodCollectPagedInput input) {
    // 声明一个支持文创品收藏查询的(拉姆达)表达式
    LambdaQueryWrapper<GoodCollect> queryWrapper = Wrappers.<GoodCollect>lambdaQuery()
        .eq(input.getId() != null && input.getId() != 0, GoodCollect::getId, input.getId())
        .eq(input.getCreatorId() != null, GoodCollect::getCreatorId, input.getCreatorId());
    // 如果前端搜索传入查询条件则拼接查询条件

    if (input.getGoodId() != null) {
      queryWrapper = queryWrapper.eq(GoodCollect::getGoodId, input.getGoodId());
    }

    if (input.getCollectUserId() != null) {
      queryWrapper = queryWrapper.eq(GoodCollect::getCollectUserId, input.getCollectUserId());
    }
    return queryWrapper;
  }

  /**
   * 处理文创品收藏对于的外键数据
   */
  private List<GoodCollectDto> DispatchItem(List<GoodCollectDto> items)
      throws InvocationTargetException, IllegalAccessException {

    for (GoodCollectDto item : items) {
      // 查询出关联的创建用户信息

      AppUser CreatorAppUserEntity = AppUserMapper
          .selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getCreatorId())).stream().findFirst()
          .orElse(new AppUser());
      item.setCreatorAppUserDto(CreatorAppUserEntity.MapToDto());

      // 查询出关联的AppUser表信息
      AppUser CollectUserEntity = AppUserMapper
          .selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getCollectUserId())).stream().findFirst()
          .orElse(new AppUser());
      item.setCollectUserDto(CollectUserEntity.MapToDto());

      // 查询出关联的Good表信息
      Good GoodEntity = GoodMapper.selectList(Wrappers.<Good>lambdaQuery().eq(Good::getId, item.getGoodId())).stream()
          .findFirst().orElse(new Good());
      item.setGoodDto(GoodEntity.MapToDto());
    }
    return items;
  }

  /**
   * 文创品收藏分页查询
   */
  @SneakyThrows
  @Override
  public PagedResult<GoodCollectDto> List(GoodCollectPagedInput input) {
    // 构建where条件+排序
    LambdaQueryWrapper<GoodCollect> queryWrapper = BuilderQuery(input);

    // 按创建时间从大到小排序 最新的显示在最前面
    queryWrapper = queryWrapper.orderByDesc(GoodCollect::getCreationTime);
    // 构建一个分页查询的model
    Page<GoodCollect> page = new Page<>(input.getPage(), input.getLimit());
    // 从数据库进行分页查询获取文创品收藏数据
    IPage<GoodCollect> pageRecords = GoodCollectMapper.selectPage(page, queryWrapper);
    // 获取所有满足条件的数据行数
    Long totalCount = GoodCollectMapper.selectCount(queryWrapper);
    // 把GoodCollect实体转换成GoodCollect传输模型
    List<GoodCollectDto> items = Extension.copyBeanList(pageRecords.getRecords(), GoodCollectDto.class);
    // 计算表的数据
    items = DispatchItem(items);

    // 返回一个分页结构给前端
    return PagedResult.GetInstance(items, totalCount);

  }

  /**
   * 单个文创品收藏查询
   */
  @SneakyThrows
  @Override
  public GoodCollectDto Get(GoodCollectPagedInput input) {
    if (input.getId() == null) {
      return new GoodCollectDto();
    }
    PagedResult<GoodCollectDto> pagedResult = List(input);
    return pagedResult.getItems().stream().findFirst().orElse(new GoodCollectDto());
  }

  /**
   * 文创品收藏创建或者修改
   */
  @SneakyThrows
  @Override
  public GoodCollectDto CreateOrEdit(GoodCollectDto input) {
    // 声明一个文创品收藏实体
    GoodCollect GoodCollect = input.MapToEntity();
    // 调用数据库的增加或者修改方法
    saveOrUpdate(GoodCollect);
    // 把传输模型返回给前端
    return GoodCollect.MapToDto();
  }

  /**
   * 文创品收藏删除
   */
  @Override
  public void Delete(IdInput input) {
    GoodCollect entity = GoodCollectMapper.selectById(input.getId());
    GoodCollectMapper.deleteById(entity);
  }

  /**
   * 文创品收藏批量删除
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
   * 文创品收藏导出
   */
  @Override
  public void Export(@RequestParam String query, HttpServletResponse response) throws IOException {

    ObjectMapper mapper = new ObjectMapper();
    GoodCollectPagedInput input = mapper.readValue(query, GoodCollectPagedInput.class);
    List<GoodCollectDto> items = List(input).getItems();
    // 声明一个工作簿
    HSSFWorkbook workbook = new HSSFWorkbook();
    // 生成一个表格，设置表格名称为"文创品收藏"
    HSSFSheet sheet = workbook.createSheet("文创品收藏表");
    // 设置表格列宽度为10个字节
    sheet.setDefaultColumnWidth(30);
    // 创建标题的显示样式
    HSSFCellStyle headerStyle = workbook.createCellStyle();
    headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    // 创建第一行表头
    HSSFRow headrow = sheet.createRow(0);
    // 表头数据
    String[] header = { "文创品名称", "用户名称", };
    // 遍历添加表头(下面模拟遍历文创品收藏，也是同样的操作过程)
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
      GoodCollectDto dto = items.get(i);
      // 创建一行
      HSSFRow row = sheet.createRow(i + 1);

      if (dto.getGoodDto() != null && Extension.isNotNullOrEmpty(dto.getGoodDto().getName())) {
        row.createCell(0).setCellValue(new HSSFRichTextString(dto.getGoodDto().getName()));
      }
      if (dto.getCollectUserDto() != null && Extension.isNotNullOrEmpty(dto.getCollectUserDto().getName())) {
        row.createCell(1).setCellValue(new HSSFRichTextString(dto.getCollectUserDto().getName()));
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
