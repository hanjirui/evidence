package com.court.evidence.service;

import com.court.evidence.es.model.ReportTreeNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReportTreeServiceTest {
    @Autowired
    private ReportTreeService reportTreeService;
    @Test
    public void testFindTreeNodeByCaseId(){
        for(ReportTreeNode node : reportTreeService.findTreeNodeByCaseId("5DD77A9F")){
            System.out.println(node);
        }
    }
}
