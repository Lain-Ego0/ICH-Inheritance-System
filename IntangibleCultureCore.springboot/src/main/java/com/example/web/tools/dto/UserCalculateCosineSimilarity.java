package com.example.web.tools.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 用户之间的相似度
 */
@AllArgsConstructor
@Data
public class UserCalculateCosineSimilarity {
    private Integer UserId1;

    private Integer UserId2;


    /**
     * 对方的itemIds
     */
    private List<Integer> itemIds;

    private Double Similarity;

}
