package com.court.evidence.manager;

import com.court.evidence.config.SpringContextHolder;
import com.court.evidence.es.model.ReportTreeNode;
import com.court.evidence.es.repository.EvidenceRepository;
import com.court.evidence.es.model.BaseEvidenceModel;
import com.court.evidence.es.model.Evidence;
import com.court.evidence.es.model.Report;
import com.court.evidence.es.repository.ReportRepository;
import com.court.evidence.es.repository.ReportTreeNodeRepository;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
public class ModelCollector {
    private static final Mapper MAPPER = SpringContextHolder.getBean(Mapper.class);
    private static final EvidenceRepository evidenceRepository = SpringContextHolder.getBean(EvidenceRepository.class);
    private static final ReportRepository reportSummaryRepository = SpringContextHolder.getBean(ReportRepository.class);
    private static final ReportTreeNodeRepository reportTreeNodeRepository = SpringContextHolder.getBean(ReportTreeNodeRepository.class);

    public static void receiveTree(List<ReportTreeNode> treeNodeList){
        System.out.println(treeNodeList);
        reportTreeNodeRepository.saveAll(treeNodeList);
    }

    public static void receive(Report reportSummary){
        System.out.println(reportSummary);
        reportSummaryRepository.save(reportSummary);
    }

    public static void receive(List<? extends BaseEvidenceModel> searchModelList){
        if(CollectionUtils.isEmpty(searchModelList)){
            return;
        }
        List<Evidence> esSearchModelList = Lists.newArrayList();
        for(BaseEvidenceModel searchModel : searchModelList){
            Evidence esSearchModel = MAPPER.map(searchModel, Evidence.class);
            esSearchModelList.add(esSearchModel);
        }
        System.out.println(esSearchModelList);
        evidenceRepository.saveAll(esSearchModelList);
    }
}
