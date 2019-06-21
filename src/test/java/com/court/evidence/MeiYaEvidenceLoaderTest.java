package com.court.evidence;

import com.court.evidence.meiya.dc_4501v3_v2_6_26201.MeiYaV2_6_26201HtmlParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MeiYaEvidenceLoaderTest {
    @Test
    public void testLoadEvidence(){
        String dic = "/Users/jirui/Downloads/20190528_测试_20190528143255_html/Report/";
		try {
			new MeiYaV2_6_26201HtmlParser().parse(dic);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
