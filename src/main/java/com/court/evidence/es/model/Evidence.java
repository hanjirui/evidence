package com.court.evidence.es.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 *  对应ES存储结构，需要映射器转换为业务实体对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Document(indexName = "evidence", type = "_doc", createIndex = false)
public class Evidence extends BaseEvidenceModel {
    public static final List<String> SEARCH_FIELDS = Lists.newArrayList("v1", "v2", "v3", "v4", "v5", "v6", "v7", "v8", "v9", "v10", "v11", "v12", "v13", "v14", "v15");
    private String v1;
    private String v2;
    private String v3;
    private String v4;
    private String v5;
    private String v6;
    private String v7;
    private String v8;
    private String v9;
    private String v10;
    private String v11;
    private String v12;
    private String v13;
    private String v14;
    private String v15;

    /**
     * 非搜索字段
     */
    private String nv1;
    private String nv2;
    private String nv3;
    private String nv4;
    private String nv5;
    private String nv6;
    private String nv7;
    private String nv8;
    private String nv9;
    private String nv10;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime t1;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime t2;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime t3;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime t4;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime t5;


    private BigDecimal f1;
    private BigDecimal f2;
    private BigDecimal f3;
    private BigDecimal f4;
    private BigDecimal f5;
    private BigDecimal f6;
    private BigDecimal f7;
    private BigDecimal f8;
    private BigDecimal f9;
    private BigDecimal f10;
}
