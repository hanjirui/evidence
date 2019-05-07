package com.court.evidence.domain;

import com.court.evidence.es.model.BaseEvidenceModel;
import lombok.*;

/**
 * 应用统计，即数据统计
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppStatistic extends BaseEvidenceModel {
    private String appName;
    private Integer eventCount;
    private Integer contactsCount;
    private Integer fileCount;
    private Integer deleteCount;
    private Integer totalCount;
}
