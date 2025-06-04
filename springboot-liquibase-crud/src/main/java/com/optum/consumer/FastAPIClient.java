package com.optum.consumer;

import com.optum.dto.LabelboxProject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.optum.dto.HcpEventPayload;

import java.util.HashMap;
import java.util.Map;

@Component
public class FastAPIClient {

    @Value("${fastapi.project.url}")
    private String fastApiUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    public String createLabelboxProject(LabelboxProject labelboxProject) {
        ResponseEntity<Map> response = restTemplate.postForEntity(fastApiUrl, labelboxProject, Map.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return (String) response.getBody().get("lbProjectId");
        }
        throw new RuntimeException("Failed to create Labelbox project from FASTAPI");
    }
}
