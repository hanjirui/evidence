package com.court.evidence.enums;

public enum ZeroOne {
    ZERO(0), ONE(1);

    private int value;

    ZeroOne(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
