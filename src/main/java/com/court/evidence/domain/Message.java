package com.court.evidence.domain;

import com.court.evidence.es.model.BaseEvidenceModel;
import lombok.*;

/**
 * 通信信息，对应内容：短信消息，通话记录信息，社交聊天信息
 */
@Data
@Builder
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Message extends BaseEvidenceModel {
    private String avatar;
    private String displayName;
    private String content;
    private String chatType;
}
