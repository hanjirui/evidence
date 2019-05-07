package com.court.evidence.es.model;

import com.court.evidence.domain.PropertyPair;
import com.court.evidence.enums.ZeroOne;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 业务搜索实体基类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEvidenceModel extends BaseModel {
    /**
     * 报告序列号
     */
    private String caseId;
    /**
     * 目录树ID
     */
    private Integer nodeId;
    /**
     * 实体类型
     */
    private String modelType;
    /**
     * 主分类
     */
    private String category;
    /**
     * 排序号
     */
    private Integer orderNumber;
    /**
     * 行为时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime actTime;
    /**
     * 消息流向
     */
    private Integer flowDirection;
    /**
     * 人工删除标志
     */
    @Builder.Default
    private Integer deleteFlag = ZeroOne.ZERO.getValue();

    private String md5;

    private List<PropertyPair> pairs;
}