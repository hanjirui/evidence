package com.court.evidence.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.client.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

@Configuration
public class ElasticSearchTemplateConfig {

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate(Client client, ObjectMapper objectMapper) {
        try {
            return new ElasticsearchTemplate(client, new CustomerEntityMapper(objectMapper));
        }
        catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }
}
