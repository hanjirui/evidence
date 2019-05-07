package com.court.evidence.service.impl;

import com.court.evidence.es.model.ReportTreeNode;
import com.court.evidence.es.repository.ReportTreeNodeRepository;
import com.court.evidence.service.ReportTreeService;
import com.google.common.collect.Lists;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportTreeServiceImpl implements ReportTreeService {
    @Autowired
    private ReportTreeNodeRepository reportTreeNodeRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public List<ReportTreeNode> findTreeNodeByCaseId(String caseId) {
        QueryBuilder queryBuilder = QueryBuilders.termQuery("caseId.raw", caseId);
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withIndices("report_tree_node").withQuery(queryBuilder).withSort(SortBuilders.fieldSort("nodeId").sortMode(SortMode.MIN)).build();
        long count = elasticsearchTemplate.count(searchQuery);
        if(count == 0){
            return Lists.newArrayList();
        }
        searchQuery.setPageable(PageRequest.of(0, (int)count));
        Page<ReportTreeNode> reportTreeNodePage = reportTreeNodeRepository.search(searchQuery);
        return reportTreeNodePage.stream().collect(Collectors.toList());
    }
}
