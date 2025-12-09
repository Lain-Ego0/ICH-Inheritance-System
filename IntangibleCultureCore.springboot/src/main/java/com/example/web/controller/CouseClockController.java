package com.example.web.controller;

import com.example.web.dto.CouseClockDto;
import com.example.web.dto.query.CouseClockPagedInput;
import com.example.web.mapper.CouseClockMapper;
import com.example.web.service.CouseClockService;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 课程打卡控制器
 */
@RestController()
@RequestMapping("/CouseClock")
public class CouseClockController {
    @Autowired()
    private  CouseClockService CouseClockService;
    @Autowired()
    private CouseClockMapper CouseClockMapper;
    /**
     * 课程打卡分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<CouseClockDto> List(@RequestBody CouseClockPagedInput input)  {
        return CouseClockService.List(input);
    }
     /**
     * 单个课程打卡查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public CouseClockDto Get(@RequestBody CouseClockPagedInput input) {

        return CouseClockService.Get(input);
    }
  
    /**
     * 课程打卡创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public CouseClockDto CreateOrEdit(@RequestBody CouseClockDto input) throws Exception {
        return CouseClockService.CreateOrEdit(input);
    }
    /**
     * 课程打卡删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input)
    {
        CouseClockService.Delete(input);
    }

    /**
     * 课程打卡批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input)
    {
        CouseClockService.BatchDelete(input);
    }
  

 
}
