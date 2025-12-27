package com.example.web.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.poi.hpsf.Decimal;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.BeanUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.sql.Date;
import java.sql.Timestamp;
import lombok.Data;
import java.time.LocalDateTime;
import com.example.web.dto.*;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
/**
 * 传人表
 */
@Data
@TableName("`Inheritor`")
public class Inheritor extends BaseEntity {


    /**
     * 姓名
     */
    @JsonProperty("Name")
    @TableField(value="Name",updateStrategy = FieldStrategy.IGNORED)
    private String Name;

    /**
     * 头像
     */
    @JsonProperty("ImageUrls")
    @TableField(value="ImageUrls",updateStrategy = FieldStrategy.IGNORED)
    private String ImageUrls;

    /**
     * 内容
     */
    @JsonProperty("Content")
    @TableField(value="Content",updateStrategy = FieldStrategy.IGNORED)
    private String Content;

    /**
     * 性别
     */
    @JsonProperty("Sex")
    @TableField(value="Sex",updateStrategy = FieldStrategy.IGNORED)
    private String Sex;

    /**
     * 民族
     */
    @JsonProperty("Nation")
    @TableField(value="Nation",updateStrategy = FieldStrategy.IGNORED)
    private String Nation;

    /**
     * 编号
     */
    @JsonProperty("No")
    @TableField(value="No",updateStrategy = FieldStrategy.IGNORED)
    private String No;

    /**
     * 出生年月
     */
    @JsonProperty("BirthDate")
    @TableField(value="BirthDate",updateStrategy = FieldStrategy.IGNORED)
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime BirthDate;

    /**
     * 省市区
     */
    @JsonProperty("ProviceCityArea")
    @TableField(value="ProviceCityArea",updateStrategy = FieldStrategy.IGNORED)
    private String ProviceCityArea;

    /**
     * 把传人实体转换成传人传输模型
     */
    public InheritorDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        InheritorDto InheritorDto = new InheritorDto();

        BeanUtils.copyProperties(InheritorDto,this);

        return InheritorDto;
    }

}
