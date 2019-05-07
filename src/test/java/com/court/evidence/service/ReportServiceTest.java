package com.court.evidence.service;

import com.court.evidence.dto.PageResultDto;
import com.court.evidence.es.model.Report;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReportServiceTest {
    @Autowired
    private ReportService reportService;
    @Test
    public void testSearchReport(){
        PageResultDto<Report> resultDto = reportService.searchReport(1, 10, "内蒙");
        System.out.println(resultDto.getPage());
        System.out.println(resultDto.getResult());
    }

    @Test
    public void testFindByCaseId(){
        System.out.println(reportService.findByCaseId("5DD77A9F"));
    }

    @Test
    public void testUpdateReport(){
        Report report = reportService.findByCaseId("5DD77A9F");
        report.setEvidenceName("华为_Mate7");
        reportService.update(report);
    }
}
