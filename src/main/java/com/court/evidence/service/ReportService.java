package com.court.evidence.service;

import com.court.evidence.es.model.Report;
import com.court.evidence.dto.PageResultDto;

public interface ReportService {

    PageResultDto<Report> searchReport(Integer page, Integer pageSize, String keyword);
    Report findByCaseId(String caseId);
    void update(Report report);
}
