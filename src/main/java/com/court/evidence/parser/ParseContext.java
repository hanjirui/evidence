package com.court.evidence.parser;

import com.court.evidence.enums.ModelType;
import com.court.evidence.enums.YesNo;
import com.court.evidence.es.model.Report;
import com.court.evidence.es.model.ReportTreeNode;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 线程不安全，一个parser独自持有一个ParseContext
 */
public class ParseContext {
    public static final int ROOT_LEVEL = 0;
    public static final String CATEGORY_SEPARATOR = " ___ ";
    private AtomicInteger sortId = new AtomicInteger();
    private String caseId = UUID.randomUUID().toString();

    private Report reportSummary;
    private TreeMap<Integer, ReportTreeNode> reportTreeMap = Maps.newTreeMap();
    private Map<Integer, Parser> parserMap = Maps.newHashMap();

    /**
     * zTreeNodeList to reportTreeMap
     * zTreeNodeList 对应js树节点集合
     * reportTreeMap 是ReportTreeNode集合，对应es mapping
     * @param zTreeNodeList
     */
    public void fromZTreeNodeList(List<ZTreeNode> zTreeNodeList){
        for(ZTreeNode zTreeNode : zTreeNodeList){
            ReportTreeNode parentNode = parentNode(zTreeNode.getLevel());
            if(parentNode == null && ROOT_LEVEL != zTreeNode.getLevel()){
                throw new RuntimeException("parse tree error, not find parent");
            }
            ReportTreeNode treeNode = ReportTreeNode.builder().build();
            treeNode.setNodeId(sortId.getAndIncrement());
            treeNode.setCaseId(caseId);
            treeNode.setLeafFlag(zTreeNode.isLeaf() ? YesNo.YES.getValue() : YesNo.NO.getValue());
            treeNode.setLevel(zTreeNode.getLevel());
            treeNode.setNodeName(zTreeNode.getName());
            treeNode.setCategory(zTreeNode.getName());
            treeNode.setOriginJumpUrl(zTreeNode.getUrl());
            if(parentNode != null){
                treeNode.setParentNodeId(parentNode.getNodeId());
                treeNode.setCategory(parentNode.getCategory() + CATEGORY_SEPARATOR + zTreeNode.getName());
            }
            reportTreeMap.put(treeNode.getNodeId(), treeNode);
        }
    }

    /**
     * 确定节点数据类型和解析器
     * @param nodeId
     * @param modelType
     * @param parser
     */
    public void confirmModelTypeAndParser(Integer nodeId, ModelType modelType, Parser parser){
        if(modelType != null && parser != null){
            parserMap.put(nodeId, parser);
            reportTreeMap.get(nodeId).setModelType(modelType.getValue());
        }
    }

    public Map<Integer, String> getCategoryPath(ReportTreeNode reportTreeNode){
        String[] categoryPathArray = reportTreeNode.getCategory().split(CATEGORY_SEPARATOR);
        Map<Integer, String> categoryPathMap = Maps.newHashMap();
        for(int i = 0; i < categoryPathArray.length; i++){
            categoryPathMap.put(i, categoryPathArray[i]);
        }
        return categoryPathMap;
    }

    public List<ReportTreeNode> treeNodeList(){
        return reportTreeMap.values().stream().collect(Collectors.toList());
    }

    public Report getReportSummary(){
        return reportSummary;
    }

    public void setReportSummary(Report reportSummary){
        this.reportSummary = reportSummary;
        this.reportSummary.setCaseId(caseId);
    }

    private ReportTreeNode parentNode(Integer level){
        if(ROOT_LEVEL == level){
            return null;
        }
        for(Integer nodeId : reportTreeMap.descendingKeySet()){
            if(reportTreeMap.get(nodeId).getLevel().equals(level - 1)){
                return reportTreeMap.get(nodeId);
            }
        }
        return null;
    }
}
