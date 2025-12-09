package com.example.web.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.web.dto.CouseCollectDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
/**
 * 课程收藏表
 */
@Data
@TableName("`CouseCollect`")
public class CouseCollect extends BaseEntity {

      
    /**
     * 课程
     */  
    @JsonProperty("CouseId")
    @TableField(value="CouseId",updateStrategy = FieldStrategy.IGNORED)
    private Integer CouseId;          
      
    /**
     * 收藏人
     */  
    @JsonProperty("CollectUserId")
    @TableField(value="CollectUserId",updateStrategy = FieldStrategy.IGNORED)
    private Integer CollectUserId;          
  
    /**
     * 把课程收藏实体转换成课程收藏传输模型
     */
    public CouseCollectDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        CouseCollectDto CouseCollectDto = new CouseCollectDto();
       
        BeanUtils.copyProperties(CouseCollectDto,this);
       
        return CouseCollectDto;
    }

}
