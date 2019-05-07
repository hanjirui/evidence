package com.court.evidence.es.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

/**
 * 报告
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "report", type = "_doc", createIndex = false)
public class Report extends BaseModel {
    private String caseId;
    private String caseName;
    private String investigator;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reportTime;
    private String softwareName;
    private String version;
    private String evidenceName;
}
