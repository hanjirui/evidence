package com.court.evidence.rest;

import com.court.evidence.es.model.Report;
import com.court.evidence.dto.PageResultDto;
import com.court.evidence.rest.request.ReportSearchRequest;
import com.court.evidence.rest.response.BaseResponse;
import com.court.evidence.service.ReportService;
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
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping
    @ResponseBody
    public BaseResponse<List<Report>> search(@Valid @RequestParam(defaultValue = "1") @Min(1)@Max(20) Integer page,
                                             @Valid @RequestParam(defaultValue = "10") @Min(1)@Max(20) Integer pageSize,
                                             @Valid @RequestBody ReportSearchRequest searchRequest){
        PageResultDto<Report> pageResultDto = reportService.searchReport(page, pageSize, searchRequest.getKeyword());
        return new BaseResponse<>(pageResultDto);
    }
}
