package com.court.evidence.domain;

import com.court.evidence.es.model.BaseEvidenceModel;
import lombok.*;

/**
 * 基本文件或操作行为，对应内容：媒体文件；媒体分类；社交绑定信息；社交删除历史；社交好友验证；社交朋友圈；邮箱邮件；应用分析；智能挖掘；
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaFile extends BaseEvidenceModel {
    private String fileType;
    private String content;
    private String path;
}
