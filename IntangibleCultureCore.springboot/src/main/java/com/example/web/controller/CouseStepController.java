package com.example.web.controller;

import com.example.web.dto.CouseStepDto;
import com.example.web.dto.query.CouseStepPagedInput;
import com.example.web.mapper.CouseStepMapper;
import com.example.web.service.CouseStepService;
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
 * 课程章节控制器
 */
@RestController()
@RequestMapping("/CouseStep")
public class CouseStepController {
    @Autowired()
    private CouseStepService CouseStepService;
    @Autowired()
    private CouseStepMapper CouseStepMapper;

    /**
     * 课程章节分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<CouseStepDto> List(@RequestBody CouseStepPagedInput input) {
        return CouseStepService.List(input);
    }

    /**
     * 单个课程章节查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public CouseStepDto Get(@RequestBody CouseStepPagedInput input) {

        return CouseStepService.Get(input);
    }

    /**
     * 课程章节创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public CouseStepDto CreateOrEdit(@RequestBody CouseStepDto input) throws Exception {
        return CouseStepService.CreateOrEdit(input);
    }

    /**
     * 课程章节删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input) {
        CouseStepService.Delete(input);
    }

    /**
     * 课程章节批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input) {
        CouseStepService.BatchDelete(input);
    }

}
