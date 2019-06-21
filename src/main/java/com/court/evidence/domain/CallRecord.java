package com.court.evidence.domain;

import com.court.evidence.es.model.BaseEvidenceModel;
import lombok.*;

/**
 * 通话记录
 */
@Data
@Builder
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CallRecord extends BaseEvidenceModel {
    private String name;
    private String phoneNumber;
    private Integer duration;
    private String cardSource;
    private String cardType;
}
