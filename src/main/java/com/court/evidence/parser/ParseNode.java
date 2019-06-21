package com.court.evidence.parser;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.ToString;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Data
public class ParseNode {
    private Integer nodeId;
    private int level;
    private String name;
    private List<ParseNode> children = Lists.newArrayList();

    public void addChild(ParseNode child){
        children.add(child);
    }

    @Override
    public String toString() {
        return "[" +
                "level=" + level +
                ", name=" + name +
                ']';
    }

    public static void print(Integer level, List<ParseNode> parseNodeList){
        if(level == null){
            level = 0;
        }
//        if(level > 9){
//            return;
//        }
        if(!CollectionUtils.isEmpty(parseNodeList)){
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while (i < level){
                sb.append("\t");
                i++;
            }
            for(ParseNode parseNode : parseNodeList){
                System.out.println(sb.toString() + parseNode);
                if(parseNode.getName().contains("分区1[hda0]")){
                    continue;
                }
                print(level + 1, parseNode.getChildren());
            }
        }
    }
}
