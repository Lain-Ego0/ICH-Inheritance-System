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
 * 视频展览收藏控制器
 */
@RestController()
@RequestMapping("/VideoExhibitionCollect")
public class VideoExhibitionCollectController {
    @Autowired()
    private  VideoExhibitionCollectService VideoExhibitionCollectService;
    @Autowired()
    private VideoExhibitionCollectMapper VideoExhibitionCollectMapper;
    /**
     * 视频展览收藏分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<VideoExhibitionCollectDto> List(@RequestBody VideoExhibitionCollectPagedInput input)  {
        return VideoExhibitionCollectService.List(input);
    }
     /**
     * 单个视频展览收藏查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public VideoExhibitionCollectDto Get(@RequestBody VideoExhibitionCollectPagedInput input) {

        return VideoExhibitionCollectService.Get(input);
    }
  
    /**
     * 视频展览收藏创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public VideoExhibitionCollectDto CreateOrEdit(@RequestBody VideoExhibitionCollectDto input) throws Exception {
        return VideoExhibitionCollectService.CreateOrEdit(input);
    }
    /**
     * 视频展览收藏删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input)
    {
        VideoExhibitionCollectService.Delete(input);
    }

    /**
     * 视频展览收藏批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input)
    {
        VideoExhibitionCollectService.BatchDelete(input);
    }
  

 
}
