package com.court.evidence.util;

import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static LocalDateTime parseTime(String timeText, String pattern){
        if(StringUtils.isEmpty(timeText)){
            return null;
        }
        if(pattern == null){
            throw new RuntimeException("error time pattern");
        }
        return LocalDateTime.parse(timeText.trim(), DateTimeFormatter.ofPattern(pattern));
    }


    public static String format(LocalDateTime time, String pattern){
        if(time == null){
            return null;
        }
        if(pattern == null){
            throw new RuntimeException("error time pattern");
        }
        return DateTimeFormatter.ofPattern(pattern).format(time);
    }
}
