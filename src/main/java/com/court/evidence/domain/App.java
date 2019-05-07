package com.court.evidence.domain;

import com.court.evidence.es.model.BaseEvidenceModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 应用列表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class App extends BaseEvidenceModel {
    private String appName;
    private String version;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime installTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime upgradeTime;
    private String packageName;
}
