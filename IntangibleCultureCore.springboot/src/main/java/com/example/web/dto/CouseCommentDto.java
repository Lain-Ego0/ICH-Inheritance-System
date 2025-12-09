package com.example.web.dto;

import com.example.web.entity.CouseComment;
import com.example.web.tools.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 课程评价类
 */
@Data
public class CouseCommentDto extends BaseDto {

    /**
     * 课程
     */
    @JsonProperty("CouseId")
    private Integer CouseId;

    /**
     * 风格评分
     */
    @JsonProperty("FeatureCommentScore")
    private Double FeatureCommentScore;

    /**
     * 表达评分
     */
    @JsonProperty("ExpressionCommentScore")
    private Double ExpressionCommentScore;

    /**
     * 质量评分
     */
    @JsonProperty("QualityCommentScore")
    private Double QualityCommentScore;

    /**
     * 评语
     */
    @JsonProperty("Comment")
    private String Comment;

    /**
     * 评语人
     */
    @JsonProperty("UserId")
    private Integer UserId;

    @JsonProperty("UserDto")
    private AppUserDto UserDto;

    @JsonProperty("CouseDto")
    private CouseDto CouseDto;

    @JsonProperty("CouseStepId")
    private Integer CouseStepId;

    @JsonProperty("CouseStepDto")
    private CouseStepDto CouseStepDto;

    /**
     * 把课程评价传输模型转换成课程评价实体
     */
    public CouseComment MapToEntity() throws InvocationTargetException, IllegalAccessException {
        CouseComment CouseComment = new CouseComment();

        BeanUtils.copyProperties(CouseComment, this);

        return CouseComment;
    }

}
