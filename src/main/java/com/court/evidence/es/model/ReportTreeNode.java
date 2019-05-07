package com.court.evidence.es.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "report_tree_node", type = "_doc", createIndex = false)
public class ReportTreeNode extends BaseModel implements Comparable<ReportTreeNode> {
    private Integer nodeId;
    private Integer parentNodeId;
    private Integer level;
    private Integer leafFlag;
    private String caseId;
    private String modelType;
    private String nodeName;
    private String category;
    private String subCategory1;
    private String subCategory2;
    private String subCategory3;
    private String subCategory4;
    private String subCategory5;
    private String subCategory6;
    private String subCategory7;
    private String subCategory8;
    private String subCategory9;
    private String subCategory10;

    @Override
    public int compareTo(ReportTreeNode o) {
        return this.nodeId.compareTo(o.nodeId);
    }
}
