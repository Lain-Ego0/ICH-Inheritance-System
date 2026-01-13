package com.example.web.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.*;
import com.example.web.dto.query.*;
import com.example.web.entity.*;
import com.example.web.tools.dto.*;
import com.example.web.enums.*;
import java.lang.reflect.InvocationTargetException;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;
import jakarta.servlet.http.HttpServletResponse;
/**
 * 传人功能的Service接口的定义清单
 */
public interface InheritorService extends IService<Inheritor> {

    /**
     * 传人的分页查询方法接口定义
     */
    public PagedResult<InheritorDto> List(InheritorPagedInput input) ;
    /**
     * 传人的新增或者修改方法接口定义
     */
    public InheritorDto CreateOrEdit(InheritorDto input);

     /**
     * 获取传人信息
     */
    public InheritorDto Get(InheritorPagedInput input);
 	 /**
     * 传人删除
     */
    public void Delete(IdInput input);

    /**
     * 传人批量删除
     */
    public void BatchDelete(IdsInput input);
  

}
