package com.court.evidence.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.elasticsearch.core.EntityMapper;

import java.io.IOException;

public class CustomerEntityMapper implements EntityMapper {
    private ObjectMapper objectMapper;

    public CustomerEntityMapper(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @Override
    public String mapToString(Object object) throws IOException {
        return objectMapper.writeValueAsString(object);
    }

    @Override
    public <T> T mapToObject(String source, Class<T> clazz) throws IOException {
        return objectMapper.readValue(source, clazz);
    }
}
