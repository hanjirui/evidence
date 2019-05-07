package com.court.evidence.es.mapper;

import com.court.evidence.es.model.Evidence;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
public class EvidenceHighlightMapper implements SearchResultMapper {

    private ObjectMapper objectMapper;

    public EvidenceHighlightMapper(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }
    @Override
    public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
        long totalHits = response.getHits().getTotalHits();
        float maxScore = response.getHits().getMaxScore();
        List<T> results = Lists.newArrayList();
        for (SearchHit hit : response.getHits())
            if (hit != null) {
                T result = null;
                try {
                    result = objectMapper.readValue(hit.getSourceAsString(), clazz);
                    mapHighlight(hit, (Evidence)result);
                } catch (IOException e) {
                    log.error("map error", e);
                }
                results.add(result);
            }
        return new AggregatedPageImpl<>(results, pageable, totalHits, response.getAggregations(), response.getScrollId(), maxScore);
    }

    private void mapHighlight(SearchHit hit, Evidence evidence){
        for(Map.Entry<String, HighlightField> entry : hit.getHighlightFields().entrySet()){
            String value = entry.getValue().getFragments()[0].toString();
            switch (entry.getKey()){
                case "v1":
                    evidence.setV1(value);
                    break;
                case "v2":
                    evidence.setV2(value);
                    break;
                case "v3":
                    evidence.setV3(value);
                    break;
                case "v4":
                    evidence.setV4(value);
                    break;
                case "v5":
                    evidence.setV5(value);
                    break;
                case "v6":
                    evidence.setV6(value);
                    break;
                case "v7":
                    evidence.setV7(value);
                    break;
                case "v8":
                    evidence.setV8(value);
                    break;
                case "v9":
                    evidence.setV9(value);
                    break;
                case "v10":
                    evidence.setV10(value);
                    break;
                case "v11":
                    evidence.setV11(value);
                    break;
                case "v12":
                    evidence.setV12(value);
                    break;
                case "v13":
                    evidence.setV13(value);
                    break;
                case "v14":
                    evidence.setV14(value);
                    break;
                case "v15":
                    evidence.setV15(value);
                    break;
                default:
                    log.error("error highlight field");
                    break;
            }
        }
    }
}
