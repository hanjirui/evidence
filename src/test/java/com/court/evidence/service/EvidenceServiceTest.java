package com.court.evidence.service;

import com.court.evidence.es.model.BaseEvidenceModel;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EvidenceServiceTest {
    @Autowired
    private EvidenceService evidenceService;
    @Test
    public void testSearch(){
        for(BaseEvidenceModel model : evidenceService.search(Lists.newArrayList("5DD77A9F"), "房租", null, null, 1, 10).getResult()){
            System.out.println(model);
        }
    }
}
