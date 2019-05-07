package com.court.evidence.enums;

public enum FlowDirection {
    IN(0, "收到"), OUT(1, "发出");
    private Integer value;
    private String name;
    FlowDirection(Integer value, String name){
        this.value = value;
        this.name = name;
    }

    public Integer getValue(){
        return value;
    }

    public String getName(){
        return name;
    }
}
