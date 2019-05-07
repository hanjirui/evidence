package com.court.evidence.pinghang;

import com.court.evidence.enums.CategoryLevel;

import java.util.HashMap;
import java.util.Map;

public class PingHangTitleLevel {
    private static Map<String, CategoryLevel> titleLevelMap = new HashMap<>();
    static {
        titleLevelMap.put("title", CategoryLevel.ROOT);
        titleLevelMap.put("subtitle", CategoryLevel.FIRST);
        titleLevelMap.put("subtitle2", CategoryLevel.SECOND);
        titleLevelMap.put("subtitle3", CategoryLevel.THIRD);
        titleLevelMap.put("subtitle4", CategoryLevel.FOURTH);
        titleLevelMap.put("subtitle5", CategoryLevel.FIFTH);
    }

    public static CategoryLevel getLevel(String title){
        return titleLevelMap.get(title);
    }
}
