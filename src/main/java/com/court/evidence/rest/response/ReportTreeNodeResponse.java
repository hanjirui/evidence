package com.court.evidence.rest.response;

import com.court.evidence.es.model.ReportTreeNode;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.TreeMap;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportTreeNodeResponse {
    private String id;
    private Integer nodeId;
    private Integer parentNodeId;
    private String treeIdPath;
    private String caseId;
    private Integer level;
    private Integer leafFlag;
    private String modelType;
    private String category;
    private String nodeName;
    private String icon;
    private String originJumperUrl;

    @Builder.Default
    private List<ReportTreeNodeResponse> children = Lists.newArrayList();

    public void addChildren(ReportTreeNodeResponse childrenNode){
        children.add(childrenNode);
    }

    public static List<ReportTreeNodeResponse> convertFromDomain(List<ReportTreeNode> nodeList){
        TreeMap<Integer, ReportTreeNodeResponse> viewTreeMap = Maps.newTreeMap();
        List<ReportTreeNodeResponse> viewList = Lists.newArrayList();
        for(ReportTreeNode node : nodeList){
            ReportTreeNodeResponse nodeView = ReportTreeNodeResponse.builder().build();
            BeanUtils.copyProperties(node, nodeView, "children");
            viewTreeMap.put(nodeView.getNodeId(), nodeView);
            if(nodeView.getParentNodeId() != null){
                viewTreeMap.get(nodeView.getParentNodeId()).addChildren(nodeView);
            } else {
                viewList.add(nodeView);
            }
        }
        return viewList;
    }
}
