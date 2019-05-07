package com.court.evidence.enums;

public enum DeleteFlagType {
    UN_DELETE(0, "undelete"), DELETE(1, "delete");

    private Integer value;
    private String strValue;

    DeleteFlagType(Integer value, String strValue){
        this.value = value;
        this.strValue = strValue;
    }

    public Integer getValue(){
        return value;
    }

    public String getStrValue(){
        return strValue;
    }

    public static Integer strToValue(String strValue){
        if(UN_DELETE.strValue.equals(strValue)){
            return UN_DELETE.value;
        } else if(DELETE.strValue.equals(strValue)){
            return DELETE.value;
        }
        return null;
    }
}
