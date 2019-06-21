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
    private String icon;
    private String originJumpUrl;

    @Override
    public int compareTo(ReportTreeNode o) {
        return this.nodeId.compareTo(o.nodeId);
    }
}
