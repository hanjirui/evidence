package com.court.evidence.es.repository;

import com.court.evidence.es.model.ReportTreeNode;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ReportTreeNodeRepository extends ElasticsearchRepository<ReportTreeNode, String> {
}
