package com.court.evidence.parser;

import lombok.Data;

@Data
public class ZTreeNode {
    private Integer id;
    private Integer pId;
    private String name;
    private String url;
    private String target;
    private String icon;
    private Integer open;
    private boolean leaf;
    private Integer level;
}
