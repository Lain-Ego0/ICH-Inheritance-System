package com.example.web.dto;

import com.example.web.enums.*;
import com.example.web.tools.dto.BaseDto;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.sql.Date;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.web.entity.*;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 会话集合类
 */
@Data
public class WechatCollectionDto extends BaseDto {


    /**
     * 本身用户
     */
    @JsonProperty("SelfUserId")
    private Integer SelfUserId;


    /**
     * 对方用户
     */
    @JsonProperty("OtherUserId")
    private Integer OtherUserId;


    /**
     * 最新消息
     */
    @JsonProperty("LastMessage")
    private String LastMessage;


    /**
     * 最新消息时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("LastTime")
    private LocalDateTime LastTime;

    @JsonProperty("SelfUserDto")
    private AppUserDto SelfUserDto;

    @JsonProperty("OtherUserDto")
    private AppUserDto OtherUserDto;

    /**
     * 把会话集合传输模型转换成会话集合实体
     */
    public WechatCollection MapToEntity() throws InvocationTargetException, IllegalAccessException {
        WechatCollection WechatCollection = new WechatCollection();
        BeanUtils.copyProperties(WechatCollection, this);
        return WechatCollection;
    }

}
