package com.court.evidence.service;

import com.court.evidence.es.model.BaseEvidenceModel;
import com.court.evidence.dto.PageResultDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EvidenceService {

    PageResultDto<BaseEvidenceModel> search(List<String> caseIdList, String keyword, LocalDateTime beginActTime, LocalDateTime endActTime, Integer page, Integer size);
    List<BaseEvidenceModel> findByTreeNodeId(String caseId, Integer treeNodeId);
}
