package com.court.evidence.es.repository;

import com.court.evidence.es.model.Report;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ReportRepository extends ElasticsearchRepository<Report, String> {

}
