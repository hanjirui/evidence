package com.court.evidence.es.repository;

import com.court.evidence.es.model.Evidence;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EvidenceRepository extends ElasticsearchRepository<Evidence, String> {
}
