package com.optum;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import com.optum.consumer.FastAPIClient;
import com.optum.dto.HcpEventPayload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;

public class FastAPIClientTest {

    private FastAPIClient fastAPIClient;
    private MockRestServiceServer mockServer;
    private RestTemplate restTemplate;

    private final String fastApiUrl = "http://mock-fastapi.com/create";

    @BeforeEach
    void setup() {
        restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);

        // Manually inject dependencies
        fastAPIClient = new FastAPIClient();
        TestUtils.setField(fastAPIClient, "fastApiUrl", fastApiUrl);
        TestUtils.setField(fastAPIClient, "restTemplate", restTemplate);
    }

    @Test
    void testCreateLabelboxProject_success() {
        // Arrange
        HcpEventPayload.ResourceDefinition def = new HcpEventPayload.ResourceDefinition();
        def.setProjectName("Test Project");
        def.setDataType("image");

        mockServer.expect(MockRestRequestMatchers.requestTo(fastApiUrl))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
                .andRespond(MockRestResponseCreators.withSuccess(
                        "{\"lbProjectId\": \"lb-123\"}", APPLICATION_JSON));

        // Act
        String lbProjectId = fastAPIClient.createLabelboxProject(def);

        // Assert
        assertEquals("lb-123", lbProjectId);
        mockServer.verify();
    }

    @Test
    void testCreateLabelboxProject_failure() {
        // Arrange
        HcpEventPayload.ResourceDefinition def = new HcpEventPayload.ResourceDefinition();
        def.setProjectName("Test Project");
        def.setDataType("image");

        mockServer.expect(MockRestRequestMatchers.requestTo(fastApiUrl))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.BAD_REQUEST));

        // Act + Assert
        assertThrows(RuntimeException.class, () -> {
            fastAPIClient.createLabelboxProject(def);
        });

        mockServer.verify();
    }
}
