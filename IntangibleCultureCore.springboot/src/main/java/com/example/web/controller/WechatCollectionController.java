package com.example.web.controller;

import com.example.web.SysConst;
import com.example.web.dto.*;
import com.example.web.dto.query.*;
import com.example.web.entity.*;
import com.example.web.mapper.*;
import com.example.web.service.*;
import com.example.web.tools.dto.*;
import com.example.web.tools.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 会话集合控制器
 */
@RestController()
@RequestMapping("/WechatCollection")
public class WechatCollectionController {
    @Autowired()
    private WechatCollectionService _WechatCollectionService;
    @Autowired()
    private WechatCollectionMapper _WechatCollectionMapper;

    /**
     * 会话集合分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<WechatCollectionDto> List(@RequestBody WechatCollectionPagedInput input) {
        return _WechatCollectionService.List(input);
    }

    /**
     * 单个会话集合查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public WechatCollectionDto Get(@RequestBody WechatCollectionPagedInput input) {

        return _WechatCollectionService.Get(input);
    }

    /**
     * 会话集合创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public WechatCollectionDto CreateOrEdit(@RequestBody WechatCollectionDto input) throws Exception {
        return _WechatCollectionService.CreateOrEdit(input);
    }

    /**
     * 会话集合删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input) {
        _WechatCollectionService.Delete(input);
    }

    /**
     * 会话集合批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input) {
        _WechatCollectionService.BatchDelete(input);
    }


    /**
     * 添加会话到双方列表
     */
    @SneakyThrows
    @RequestMapping(value = "/InsertWechatCollection", method = RequestMethod.POST)
    public void InsertWechatCollection(@RequestBody WechatCollectionDto input) {
        _WechatCollectionService.InsertWechatCollection(input);

    }
}
