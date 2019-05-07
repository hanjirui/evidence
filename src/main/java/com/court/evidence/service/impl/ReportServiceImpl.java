package com.court.evidence.service.impl;

import com.court.evidence.es.model.Report;
import com.court.evidence.dto.PageResultDto;
import com.court.evidence.es.repository.ReportRepository;
import com.court.evidence.service.ReportService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportRepository reportRepository;

    @Override
    public PageResultDto<Report> searchReport(Integer page, Integer pageSize, String keyword) {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        if(!StringUtils.isEmpty(keyword)){
            boolQueryBuilder.should(QueryBuilders.multiMatchQuery(keyword, "caseId", "caseName", "evidenceName"));
        }
        Page<Report> reportPage = reportRepository.search(boolQueryBuilder, PageRequest.of(page - 1, pageSize));
        return new PageResultDto(page, pageSize, (int)reportPage.getTotalElements(), reportPage.stream().collect(Collectors.toList()));
    }

    @Override
    public Report findByCaseId(String caseId) {
        QueryBuilder queryBuilder = QueryBuilders.termQuery("caseId.raw", caseId);
        Iterable<Report> reportIt= reportRepository.search(queryBuilder);
        for(Report report : reportIt){
            return report;
        }
        return null;
    }

    @Override
    public void update(Report report) {
        reportRepository.save(report);
    }
}
