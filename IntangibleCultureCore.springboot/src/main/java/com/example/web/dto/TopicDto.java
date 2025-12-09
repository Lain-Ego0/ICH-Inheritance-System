package com.example.web.dto;

import com.example.web.entity.Topic;
import com.example.web.enums.AuditStatusEnum;
import com.example.web.tools.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
/**
 * 话题类
 */
@Data
public class TopicDto extends BaseDto
{

    /**
     * 标题
     */

    @JsonProperty("Title")
    private String Title;
    /**
     * 封面
     */

    @JsonProperty("Cover")
    private String Cover;
    /**
     * 主图
     */

    @JsonProperty("ImageUrls")
    private String ImageUrls;
    /**
     * 浏览数
     */

    @JsonProperty("ViewCount")
    private Integer ViewCount;
    /**
     * 内容
     */

    @JsonProperty("Content")
    private String Content;
    /**
     * 话题类型
     */

    @JsonProperty("TopicTypeId")
    private Integer TopicTypeId;

    @JsonProperty("TopicTypeDto")
    private TopicTypeDto TopicTypeDto;

    /**
     * 评论集合
     */
    @JsonProperty("CommentDtos")
    private List<CommentDto> CommentDtos;
    /**
     * 是否推荐
     */
    @JsonProperty("IsRecommand")
    private Boolean IsRecommand;
    /**
     * 审核状态
     */
    @JsonProperty("AuditStatus")
    private Integer AuditStatus;

    /**
     * 审核状态描述
     * @return
     */
    @JsonProperty("AuditStatusFormat")
    public String AuditStatusFormat() {
        return  AuditStatusEnum.GetEnum(AuditStatus).toString();
    }

    /**
     * 把传输模型转换成实体
     */
    public Topic MapToEntity() throws InvocationTargetException, IllegalAccessException {
        Topic Topic = new Topic();
        BeanUtils.copyProperties(Topic, this);
        return Topic;
    }
}
