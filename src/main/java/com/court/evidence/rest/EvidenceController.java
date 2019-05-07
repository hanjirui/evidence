package com.court.evidence.rest;

import com.court.evidence.es.model.BaseEvidenceModel;
import com.court.evidence.dto.PageResultDto;
import com.court.evidence.rest.request.SearchRequest;
import com.court.evidence.rest.response.BaseResponse;
import com.court.evidence.service.EvidenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/evidence")
public class EvidenceController {

    @Autowired
    private EvidenceService evidenceService;

    @PostMapping
    @ResponseBody
    public BaseResponse<List<BaseEvidenceModel>> search(@Valid @RequestParam(defaultValue = "1") @Min(1)@Max(20) Integer page,
                                                        @Valid @RequestParam(defaultValue = "10") @Min(1)@Max(20) Integer pageSize,
                                                        @Valid @RequestBody @NotNull SearchRequest searchRequest){
        PageResultDto<BaseEvidenceModel> result = evidenceService.search(searchRequest.getCaseIdList(), searchRequest.getKeyword(), searchRequest.getBeginTime(), searchRequest.getEndTime(), page, pageSize);
        return new BaseResponse<>(result);
    }

    @RequestMapping("/{caseId}/{nodeId}")
    @ResponseBody
    public BaseResponse<List<BaseEvidenceModel>> reportTreeByCaseId(@Valid@ PathVariable @NotNull String caseId, @PathVariable@NotNull Integer nodeId){
        List<BaseEvidenceModel> result = evidenceService.findByTreeNodeId(caseId, nodeId);
        return new BaseResponse<>(result);
    }
}
