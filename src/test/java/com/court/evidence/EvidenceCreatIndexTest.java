package com.court.evidence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.AliasQuery;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EvidenceCreatIndexTest {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private RestTemplate restTemplate;
    @Test
    public void testCreatIndex() throws IOException {
        String indexVersion = "v1";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        // evidence mapping
        String evidenceIndex = "evidence";
        String evidenceIndexName = evidenceIndex + "_" + indexVersion;
        createIndex(evidenceIndex, evidenceIndexName);

        // report mapping
        String reportIndex = "report";
        String reportIndexName = reportIndex + "_" + indexVersion;
        createIndex(reportIndex, reportIndexName);

        // report tree node mapping
        String reportTreeNodeIndex = "report_tree_node";
        String reportTreeNodeIndexName = reportTreeNodeIndex + "_" + indexVersion;
        createIndex(reportTreeNodeIndex, reportTreeNodeIndexName);
    }

    private void createIndex(String index, String indexName) throws IOException {
        if(elasticsearchTemplate.indexExists(indexName)) {
            elasticsearchTemplate.deleteIndex(indexName);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        String evidenceMappingJson = FileCopyUtils.copyToString(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("es/mapping/" + index + ".json")));
        RequestEntity<String>  requestEntity = new RequestEntity<>(evidenceMappingJson, headers, HttpMethod.PUT, URI.create("http://localhost:9200/" + indexName + "?pretty"));
        restTemplate.exchange(requestEntity, String.class).getBody();
        AliasQuery aliasQuery = new AliasQuery();
        aliasQuery.setAliasName(index);
        aliasQuery.setIndexName(indexName);
        elasticsearchTemplate.addAlias(aliasQuery);
    }
}
