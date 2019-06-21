package com.court.evidence.enums;

public enum YesNo {
    YES(1), NO(0);

    private int value;

    YesNo(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
