package com.court.evidence.rest;

import com.court.evidence.es.model.ReportTreeNode;
import com.court.evidence.rest.response.BaseResponse;
import com.court.evidence.rest.response.ReportTreeNodeResponse;
import com.court.evidence.service.ReportTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/reportTree")
public class ReportTreeController {

    @Autowired
    private ReportTreeService reportTreeService;

    @RequestMapping("/{caseId}")
    @ResponseBody
    public BaseResponse<List<ReportTreeNodeResponse>> reportTreeByCaseId(@PathVariable @Valid @NotNull String caseId){
        List<ReportTreeNode> treeNodeList = reportTreeService.findTreeNodeByCaseId(caseId);
        return new BaseResponse<>(ReportTreeNodeResponse.convertFromDomain(treeNodeList));
    }
}
