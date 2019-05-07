package com.court.evidence.parser;

import com.court.evidence.enums.CategoryLevel;
import com.court.evidence.enums.ModelType;
import com.court.evidence.enums.ZeroOne;
import com.court.evidence.es.model.Report;
import com.court.evidence.es.model.ReportTreeNode;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 线程不安全，一个parser独自持有一个ParseContext
 */
public class ParseContext {
    private AtomicInteger sortId = new AtomicInteger();
    private TreeMap<ReportTreeNode, Parser> reportTreeMap = Maps.newTreeMap();
    private Report reportSummary;

    public void addNode(CategoryLevel level, String nodeName, boolean leafFlag, ModelType modelType, Parser parser){
        if(level == null){
            throw new RuntimeException("not support level");
        }
        ReportTreeNode parentNode = getParentNode(level.getLevel());
        if(parentNode == null && CategoryLevel.ROOT != level){
            throw new RuntimeException("parse tree error, not find parent");
        }
        ReportTreeNode treeNode = ReportTreeNode.builder().build();
        treeNode.setNodeId(sortId.getAndIncrement());
        treeNode.setCaseId(reportSummary.getCaseId());
        treeNode.setLeafFlag(leafFlag ? ZeroOne.ONE.getValue() : ZeroOne.ZERO.getValue());
        treeNode.setModelType(modelType == null ? null : modelType.getValue());
        treeNode.setLevel(level.getLevel());
        treeNode.setNodeName(nodeName);
        if(parentNode != null){
            treeNode.setParentNodeId(parentNode.getNodeId());
            treeNode.setCategory(parentNode.getCategory());
            treeNode.setSubCategory1(parentNode.getSubCategory1());
            treeNode.setSubCategory2(parentNode.getSubCategory2());
            treeNode.setSubCategory3(parentNode.getSubCategory3());
            treeNode.setSubCategory4(parentNode.getSubCategory4());
            treeNode.setSubCategory5(parentNode.getSubCategory5());
        }
        switch (level){
            case ROOT:
                treeNode.setCategory(nodeName);
                break;
            case FIRST:
                treeNode.setSubCategory1(nodeName);
                break;
            case SECOND:
                treeNode.setSubCategory2(nodeName);
                break;
            case THIRD:
                treeNode.setSubCategory3(nodeName);
                break;
            case FOURTH:
                treeNode.setSubCategory4(nodeName);
                break;
            case FIFTH:
                treeNode.setSubCategory5(nodeName);
                break;
        }
        reportTreeMap.put(treeNode, parser);
    }

    public Report getReportSummary(){
        return reportSummary;
    }

    public void setReportSummary(Report reportSummary){
        this.reportSummary = reportSummary;
    }

    public List<ReportTreeNode> treeNodeList(){
        return this.reportTreeMap.keySet().stream().collect(Collectors.toList());
    }

    public ReportTreeNode lastNode(){
        if(reportTreeMap.size() == 0){
            return null;
        }
        return reportTreeMap.lastKey();
    }
//
//    public Parser lastParser(){
//        if(reportTreeMap.size() == 0){
//            return null;
//        }
//        return reportTreeMap.lastEntry().getValue();
//    }
//
//    public void printTree(){
//        for(ReportTreeNode treeNode : reportTreeMap.keySet()){
//            CategoryLevel level = CategoryLevel.levelOf(treeNode.getLevel());
//            String prefix = "";
//            switch (level){
//                case ROOT:
//                    prefix = "";
//                    break;
//                case FIRST:
//                    prefix = "\t";
//                    break;
//                case SECOND:
//                    prefix = "\t\t";
//                    break;
//                case THIRD:
//                    prefix = "\t\t\t";
//                    break;
//                case FOURTH:
//                    prefix = "\t\t\t\t";
//                    break;
//                case FIFTH:
//                    prefix = "\t\t\t\t\t";
//                    break;
//            }
//            System.out.println(prefix + treeNode);
//        }
//    }

    private ReportTreeNode getParentNode(Integer level){
        if(CategoryLevel.ROOT.getLevel().equals(level)){
            return null;
        }
        for(ReportTreeNode node : reportTreeMap.descendingKeySet()){
            if(node.getLevel().equals(level - 1)){
                return node;
            }
        }
        return null;
    }
}
