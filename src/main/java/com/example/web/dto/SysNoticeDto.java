package com.example.web.dto;

import com.example.web.entity.SysNotice;
import com.example.web.tools.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 系统通知类
 */
@Data
public class SysNoticeDto extends BaseDto
{



    /**
     * 标题
     */
    @JsonProperty("Title")
    private String Title;


    /**
     * 是否显示
     */
    @JsonProperty("IsPutaway")
    private Boolean IsPutaway;


    /**
     * 通知内容
     */
    @JsonProperty("Content")
    private String Content;

    /**
     * 把系统通知传输模型转换成系统通知实体
     */
    public SysNotice MapToEntity() throws InvocationTargetException, IllegalAccessException {
        SysNotice SysNotice= new SysNotice();

        BeanUtils.copyProperties(SysNotice,this);

        return SysNotice;
    }

}
