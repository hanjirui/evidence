package com.court.evidence.domain;

import com.court.evidence.es.model.BaseEvidenceModel;
import lombok.*;

/**
 * 社交群组成员
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupMember extends BaseEvidenceModel {
    private String groupAccount;
    private String groupName;
    private String memberAccount;
    private String memberNickname;
    private String description;
}
