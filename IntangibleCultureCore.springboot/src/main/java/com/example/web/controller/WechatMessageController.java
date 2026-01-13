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
 * 会话消息控制器
 */
@RestController()
@RequestMapping("/WechatMessage")
public class WechatMessageController {
    @Autowired()
    private  WechatMessageService _WechatMessageService;
    @Autowired()
    private WechatMessageMapper _WechatMessageMapper;
    /**
     * 会话消息分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<WechatMessageDto> List(@RequestBody WechatMessagePagedInput input)  {
        return _WechatMessageService.List(input);
    }
     /**
     * 单个会话消息查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public WechatMessageDto Get(@RequestBody WechatMessagePagedInput input) {

        return _WechatMessageService.Get(input);
    }
  
    /**
     * 会话消息创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public WechatMessageDto CreateOrEdit(@RequestBody WechatMessageDto input) throws Exception {
        return _WechatMessageService.CreateOrEdit(input);
    }
    /**
     * 会话消息删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input)
    {
        _WechatMessageService.Delete(input);
    }

    /**
     * 会话消息批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input)
    {
        _WechatMessageService.BatchDelete(input);
    }

  /**
     * 得到管理员id
     */
    @RequestMapping(value = "/GetAdminId", method = RequestMethod.POST)
    public ResponseData<Integer> GetAdminId() {
        return ResponseData.GetResponseDataInstance(_WechatMessageService.GetAdminId(), "成功", true);
    }

    /**
     * 检测是否可以发消息
     */
    @RequestMapping(value = "/CheckCanSendMessage", method = RequestMethod.POST)
    public void CheckCanSendMessage(@RequestBody WechatMessageDto input) {
     
        _WechatMessageService.CheckCanSendMessage(input);
    }
 
}
