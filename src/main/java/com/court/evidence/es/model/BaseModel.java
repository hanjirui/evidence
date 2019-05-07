package com.court.evidence.es.model;

import lombok.*;

import java.time.LocalDateTime;

/**
 * 业务实体基类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseModel {
    private String id;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer systemDeleteFlag;
}
