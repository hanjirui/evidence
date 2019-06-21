package com.court.evidence.domain;

import com.court.evidence.es.model.BaseEvidenceModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 操作行为，对应内容：社交绑定信息；社交删除历史；社交好友验证；社交朋友圈；邮箱邮件；应用分析；智能挖掘；
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Operation extends BaseEvidenceModel {
    private String action;
    private String content;
    private String opposite;
    private String appName;
}
