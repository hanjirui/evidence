package com.court.evidence.parser;

import com.court.evidence.es.model.BaseEvidenceModel;
import org.jsoup.nodes.Element;

import java.util.List;

@FunctionalInterface
public interface Parser {
    List<? extends BaseEvidenceModel> parse(Element element);
}
