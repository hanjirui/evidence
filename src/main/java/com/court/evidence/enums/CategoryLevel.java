//package com.court.evidence.enums;
//
//import java.util.stream.Stream;
//
//public enum CategoryLevel {
//    ROOT(0),
//    FIRST(1),
//    SECOND(2),
//    THIRD(3),
//    FOURTH(4),
//    FIFTH(5);
//
//    Integer level;
//    CategoryLevel(Integer level){
//        this.level = level;
//    }
//
//    public Integer getLevel(){
//        return this.level;
//    }
//
//    public static CategoryLevel levelOf(int levelValue){
//        return Stream.of(CategoryLevel.values()).filter(cl -> cl.getLevel() == levelValue).findFirst().orElse(null);
//    }
//
//    public static void main(String [] args){
//        System.out.println(CategoryLevel.levelOf(3));
//    }
//}
