package com.court.evidence.dozer;

import org.dozer.DozerConverter;

import java.time.LocalDateTime;

public class LocalDateTimeConverter extends DozerConverter<LocalDateTime, LocalDateTime> {

    public LocalDateTimeConverter() {
        super(LocalDateTime.class, LocalDateTime.class);
    }

    @Override
    public LocalDateTime convertTo(LocalDateTime localDateTime, LocalDateTime localDateTime2) {
        if(localDateTime == null){
            return null;
        }
        return LocalDateTime.of(localDateTime.toLocalDate(), localDateTime.toLocalTime());
    }

    @Override
    public LocalDateTime convertFrom(LocalDateTime localDateTime, LocalDateTime localDateTime2) {
        if(localDateTime == null){
            return null;
        }
        return LocalDateTime.of(localDateTime.toLocalDate(), localDateTime.toLocalTime());
    }
}
