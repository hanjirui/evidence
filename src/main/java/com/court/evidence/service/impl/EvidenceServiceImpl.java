package com.court.evidence.service.impl;

import com.court.evidence.domain.*;
import com.court.evidence.dto.PageResultDto;
import com.court.evidence.enums.ModelType;
import com.court.evidence.es.mapper.EvidenceHighlightMapper;
import com.court.evidence.es.model.BaseEvidenceModel;
import com.court.evidence.es.model.Evidence;
import com.court.evidence.es.repository.EvidenceRepository;
import com.court.evidence.service.EvidenceService;
import com.court.evidence.util.DateTimeUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.dozer.Mapper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EvidenceServiceImpl implements EvidenceService {

    @Autowired
    private EvidenceRepository evidenceRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private Mapper dozerMapper;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public PageResultDto<BaseEvidenceModel> search(List<String> caseIdList, String keyword, LocalDateTime beginActTime, LocalDateTime endActTime, Integer page, Integer size) {
        BoolQueryBuilder builder = new BoolQueryBuilder();
        builder.must(QueryBuilders.termsQuery("caseId.raw", caseIdList));
        if(beginActTime != null || endActTime != null){
            RangeQueryBuilder actTimeQueryBuilder = QueryBuilders.rangeQuery("actTime");
            if(beginActTime != null){
                actTimeQueryBuilder.gte(DateTimeUtil.format(beginActTime, DateTimeUtil.DATE_TIME_PATTERN));
            }
            if(endActTime != null){
                actTimeQueryBuilder.lte(DateTimeUtil.format(endActTime, DateTimeUtil.DATE_TIME_PATTERN));
            }
            builder.must(actTimeQueryBuilder);
        }

        BoolQueryBuilder keywordQueryBuilder = new BoolQueryBuilder();
        keywordQueryBuilder.should(QueryBuilders.multiMatchQuery(keyword, Evidence.SEARCH_FIELDS.toArray(new String[Evidence.SEARCH_FIELDS.size()])));

        builder.must(keywordQueryBuilder);

        page = page == null || page < 1 ? 0 : page - 1;
        size = size == null ? 10 : size;
        List<HighlightBuilder.Field> highlightFields = Evidence.SEARCH_FIELDS.stream().map(field -> new HighlightBuilder.Field(field).preTags("<span style=\"color:red\">").postTags("</span>")).collect(Collectors.toList());
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withIndices("evidence").withHighlightFields(highlightFields.toArray(new HighlightBuilder.Field[highlightFields.size()])).withQuery(builder).withPageable(PageRequest.of(page, size)).build();

        Page<Evidence> evidencePage = elasticsearchTemplate.queryForPage(searchQuery, Evidence.class, new EvidenceHighlightMapper(objectMapper));
//        Page<Evidence> evidencePage = evidenceRepository.search(searchQuery);
        List<BaseEvidenceModel> baseEvidenceModelList = getBaseSearchModels(evidencePage);
        return new PageResultDto(page, size, (int)evidencePage.getTotalElements(), baseEvidenceModelList);
    }

    @Override
    public List<BaseEvidenceModel> findByTreeNodeId(String caseId, Integer treeNodeId) {
        BoolQueryBuilder builder = new BoolQueryBuilder();
        builder.must(QueryBuilders.termQuery("caseId.raw", caseId));
        builder.must(QueryBuilders.termQuery("nodeId", treeNodeId));
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withIndices("evidence").withQuery(builder).withSort(SortBuilders.fieldSort("orderNumber").sortMode(SortMode.MIN)).build();
        long count = elasticsearchTemplate.count(searchQuery);
        if(count <= 0){
            return Lists.newArrayList();
        }
        searchQuery.setPageable(PageRequest.of(0, (int)count));
        Page<Evidence> evidenceList = evidenceRepository.search(searchQuery);
        List<BaseEvidenceModel> baseEvidenceModelList = getBaseSearchModels(evidenceList);
        return baseEvidenceModelList;
    }

    private List<BaseEvidenceModel> getBaseSearchModels(Page<Evidence> evidenceList) {
        List<BaseEvidenceModel> baseEvidenceModelList = Lists.newArrayList();
        for(Evidence evidence : evidenceList){
            if(ModelType.REPORT_SUMMARY.getValue().equals(evidence.getModelType())){
                baseEvidenceModelList.add(dozerMapper.map(evidence, BigProperty.class));
            } else if(ModelType.MATERIAL_EVIDENCE.getValue().equals(evidence.getModelType())){
                baseEvidenceModelList.add(dozerMapper.map(evidence, BigProperty.class));
            } else if(ModelType.OWNER_INFO.getValue().equals(evidence.getModelType())){
                baseEvidenceModelList.add(dozerMapper.map(evidence, BigProperty.class));
            } else if(ModelType.APP_LIST.getValue().equals(evidence.getModelType())){
                baseEvidenceModelList.add(dozerMapper.map(evidence, App.class));
            } else if(ModelType.APP_STATISTIC.getValue().equals(evidence.getModelType())){
                baseEvidenceModelList.add(dozerMapper.map(evidence, AppStatistic.class));
            } else if(ModelType.ACCOUNT.getValue().equals(evidence.getModelType())){
                baseEvidenceModelList.add(dozerMapper.map(evidence, Account.class));
            } else if(ModelType.ADDRESS_BOOK.getValue().equals(evidence.getModelType())){
                baseEvidenceModelList.add(dozerMapper.map(evidence, Account.class));
            } else if(ModelType.LOCAL_CALL_MESSAGE_STATS.getValue().equals(evidence.getModelType())){
                baseEvidenceModelList.add(dozerMapper.map(evidence, Account.class));
            } else if(ModelType.MEDIA_FILE.getValue().equals(evidence.getModelType())){
                baseEvidenceModelList.add(dozerMapper.map(evidence, MediaFile.class));
            } else if(ModelType.OPERATION.getValue().equals(evidence.getModelType())){
                baseEvidenceModelList.add(dozerMapper.map(evidence, Operation.class));
            } else if(ModelType.SOCIAL_ACCOUNT.getValue().equals(evidence.getModelType())){
                baseEvidenceModelList.add(dozerMapper.map(evidence, BigProperty.class));
            } else if(ModelType.SOCIAL_FRIEND.getValue().equals(evidence.getModelType())){
                baseEvidenceModelList.add(dozerMapper.map(evidence, Account.class));
            } else if(ModelType.SOCIAL_GROUP.getValue().equals(evidence.getModelType())){
                baseEvidenceModelList.add(dozerMapper.map(evidence, Account.class));
            } else if(ModelType.SOCIAL_GROUP_MEMBER.getValue().equals(evidence.getModelType())){
                baseEvidenceModelList.add(dozerMapper.map(evidence, GroupMember.class));
            } else if(ModelType.EMAIL_CONTACT.getValue().equals(evidence.getModelType())){
                baseEvidenceModelList.add(dozerMapper.map(evidence, Account.class));
            } else if(ModelType.SMART_MINING_COMMON.getValue().equals(evidence.getModelType())){
                baseEvidenceModelList.add(dozerMapper.map(evidence, Operation.class));
            } else if(ModelType.SMART_MINING_FINANCE.getValue().equals(evidence.getModelType())){
                baseEvidenceModelList.add(dozerMapper.map(evidence, Operation.class));
            } else if(ModelType.SMART_MINING_AUTH_CODE.getValue().equals(evidence.getModelType())){
                baseEvidenceModelList.add(dozerMapper.map(evidence, Operation.class));
            } else if(ModelType.CHAT_MESSAGE.getValue().equals(evidence.getModelType())){
                baseEvidenceModelList.add(dozerMapper.map(evidence, Message.class));
            } else if(ModelType.OPERATION_OPPOSITE.getValue().equals(evidence.getModelType())){
                baseEvidenceModelList.add(dozerMapper.map(evidence, Operation.class));
            } else {
                throw new RuntimeException("error model type");
            }
        }
        return baseEvidenceModelList;
    }
}
