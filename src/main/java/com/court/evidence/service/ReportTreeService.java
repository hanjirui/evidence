package com.court.evidence.service;

import com.court.evidence.es.model.ReportTreeNode;

import java.util.List;

public interface ReportTreeService {

    List<ReportTreeNode> findTreeNodeByCaseId(String caseId);
}
