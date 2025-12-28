package com.example.web.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.web.dto.SysNoticeDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 系统通知表
 */
@Data
@TableName("`SysNotice`")
public class SysNotice extends BaseEntity {


    /**
     * 标题
     */
    @JsonProperty("Title")
    @TableField(value="Title",updateStrategy = FieldStrategy.IGNORED)
    private String Title;

    /**
     * 是否显示
     */
    @JsonProperty("IsPutaway")
    @TableField(value="IsPutaway",updateStrategy = FieldStrategy.IGNORED)
    private Boolean IsPutaway;

    /**
     * 通知内容
     */
    @JsonProperty("Content")
    @TableField(value="Content",updateStrategy = FieldStrategy.IGNORED)
    private String Content;

    /**
     * 把系统通知实体转换成系统通知传输模型
     */
    public SysNoticeDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        SysNoticeDto SysNoticeDto = new SysNoticeDto();

        BeanUtils.copyProperties(SysNoticeDto,this);

        return SysNoticeDto;
    }

}
