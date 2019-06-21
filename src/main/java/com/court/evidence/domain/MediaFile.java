package com.court.evidence.domain;

import com.court.evidence.es.model.BaseEvidenceModel;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaFile extends BaseEvidenceModel {
    private String fileType;
    private String content;
    private String path;
}
