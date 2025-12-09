package com.example.web.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.web.dto.CouseCommentDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 课程评价表
 */
@Data
@TableName("`CouseComment`")
public class CouseComment extends BaseEntity {

    /**
     * 课程
     */
    @JsonProperty("CouseId")
    @TableField(value = "CouseId", updateStrategy = FieldStrategy.IGNORED)
    private Integer CouseId;

    /**
     * 风格评分
     */
    @JsonProperty("FeatureCommentScore")
    @TableField(value = "FeatureCommentScore", updateStrategy = FieldStrategy.IGNORED)
    private Double FeatureCommentScore;

    /**
     * 表达评分
     */
    @JsonProperty("ExpressionCommentScore")
    @TableField(value = "ExpressionCommentScore", updateStrategy = FieldStrategy.IGNORED)
    private Double ExpressionCommentScore;

    /**
     * 质量评分
     */
    @JsonProperty("QualityCommentScore")
    @TableField(value = "QualityCommentScore", updateStrategy = FieldStrategy.IGNORED)
    private Double QualityCommentScore;

    /**
     * 评语
     */
    @JsonProperty("Comment")
    @TableField(value = "Comment", updateStrategy = FieldStrategy.IGNORED)
    private String Comment;

    /**
     * 评语人
     */
    @JsonProperty("UserId")
    @TableField(value = "UserId", updateStrategy = FieldStrategy.IGNORED)
    private Integer UserId;

    /**
     * 课程步骤
     */
    @JsonProperty("CouseStepId")
    @TableField(value = "CouseStepId", updateStrategy = FieldStrategy.IGNORED)
    private Integer CouseStepId;

    /**
     * 把课程评价实体转换成课程评价传输模型
     */
    public CouseCommentDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        CouseCommentDto CouseCommentDto = new CouseCommentDto();

        BeanUtils.copyProperties(CouseCommentDto, this);

        return CouseCommentDto;
    }

}
