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
 * 视频展览收藏功能的Service接口的定义清单
 */
public interface VideoExhibitionCollectService extends IService<VideoExhibitionCollect> {

    /**
     * 视频展览收藏的分页查询方法接口定义
     */
    public PagedResult<VideoExhibitionCollectDto> List(VideoExhibitionCollectPagedInput input) ;
    /**
     * 视频展览收藏的新增或者修改方法接口定义
     */
    public VideoExhibitionCollectDto CreateOrEdit(VideoExhibitionCollectDto input);

     /**
     * 获取视频展览收藏信息
     */
    public VideoExhibitionCollectDto Get(VideoExhibitionCollectPagedInput input);
 	 /**
     * 视频展览收藏删除
     */
    public void Delete(IdInput input);

    /**
     * 视频展览收藏批量删除
     */
    public void BatchDelete(IdsInput input);
  

}
