package com.court.evidence.domain;

import com.court.evidence.es.model.BaseEvidenceModel;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 账号，对应内容：账号信息；通讯录；社交账号；邮件联系人；
 * 可扩展为通信统计，对应内容：短信->本机统计；通话记录->本机统计；社交聊天->好友统计；社交聊天->群组统计
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseEvidenceModel {
    private String accountId;
    private String accountName;
    private String nickname;
    private String realName;
    private String appName;
    private String password;
    private String email;
    private String phoneNumber;
    private String sex;
    private String address;
    private Integer count;
    private LocalDateTime lastLoginTime;
}
