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
 * 非遗文化类别功能的Service接口的定义清单
 */
public interface CulturalTypeService extends IService<CulturalType> {

    /**
     * 非遗文化类别的分页查询方法接口定义
     */
    public PagedResult<CulturalTypeDto> List(CulturalTypePagedInput input) ;
    /**
     * 非遗文化类别的新增或者修改方法接口定义
     */
    public CulturalTypeDto CreateOrEdit(CulturalTypeDto input);

     /**
     * 获取非遗文化类别信息
     */
    public CulturalTypeDto Get(CulturalTypePagedInput input);
 	 /**
     * 非遗文化类别删除
     */
    public void Delete(IdInput input);

    /**
     * 非遗文化类别批量删除
     */
    public void BatchDelete(IdsInput input);
  

}
