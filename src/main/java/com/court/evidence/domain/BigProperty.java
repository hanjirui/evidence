package com.court.evidence.domain;

import com.court.evidence.es.model.BaseEvidenceModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态多属性实体，将多属性对以固定格式生成一个字符串存储，目前支持如下内容：报告摘要，物证信息，机主信息
 */

@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BigProperty extends BaseEvidenceModel {

    @Builder.Default
    private List<PropertyPair> pairs = new ArrayList<>();

    public void addPropertyPair(PropertyPair pair){
        pairs.add(pair);
    }

    public String getPropertyPairValue(String propertyName){
        for(PropertyPair pair : pairs){
            if (propertyName.equals(pair.getName())){
                return pair.getValue();
            }
        }
        return null;
    }

}
