package com.optum.service;

import com.optum.dto.*;
import com.optum.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class HcpEventService {

    @Autowired
    private UAISProjectRepository uaisProjectRepository;

    @Autowired
    private UserProjectRepository userProjectRepository;


    @Autowired
    private LabelboxProjectRepository labelboxProjectRepository;

    @Autowired
    private VendorProjectRepository vendorProjectRepository;

    @Autowired
    private ProjectMappingsRepository projectMappingsRepository;

    @Autowired
    private RestTemplate restTemplate;

    public void processHcpEvent(HcpEventPayload payload) {
        String uaisProjectId = payload.getResourceDefinition().getUaisProjectId();

        // Check if UAIS Project exists
        Optional<UAISProject> uaisProjectOpt = uaisProjectRepository.findById(uaisProjectId);
        if (uaisProjectOpt.isPresent()) {
            UAISProject uaisProject = uaisProjectOpt.get();

            // Get User Project based on the UAIS Project ID, since it is same,
            Optional<UserProject> userProjectOpt = userProjectRepository.findById(uaisProject.getUaisProjectId());
            UserProject userProject = userProjectOpt.get();

            // Create LabelboxProject
            LabelboxProject labelboxProject = LabelboxProject.builder()
                    .projectName(payload.getResourceDefinition().getProjectName())
                    .dataType(payload.getResourceDefinition().getDataType())
                    .tag("") // Need to discuss
                    .type(payload.getResourceType())
                    .workspaceId(uaisProject.getWorkspace().getWorkspaceId())
                    .build();

            // Submit to FAST API
            ResponseEntity<String> response = restTemplate.postForEntity(
                    "http://fastapi-service/api/projects", labelboxProject, String.class); // URL need to be updated.

            // Persist if FAST API response is successful
            if (response.getStatusCode().is2xxSuccessful()) {

                // Extract ID from response
                String lbProjectId = response.getBody();

                // Set ID to LabelboxProject
                labelboxProject.setLabelboxProjectId(lbProjectId);


                // Set ID to VendorProject
                VendorProject vendorProject = VendorProject.builder()
                        .vendorProjectId(lbProjectId)
                        .projectName(payload.getResourceDefinition().getProjectName())
                        .dataType(payload.getResourceDefinition().getDataType())
                        .tag("") // Need to discuss
                        .vendorType(payload.getResourceType()) // Need to discuss
                        .startDate(payload.getResourceDefinition().getStartDate())
                        .endDate(payload.getResourceDefinition().getEndDate())
                        .build();

                labelboxProjectRepository.save(labelboxProject);
                vendorProjectRepository.save(vendorProject);

                // Create ProjectMappings
                ProjectMappings projectMapping = ProjectMappings.builder()
                        .projectMappingId(UUID.randomUUID().toString()) // Need to discuss
                        .userProject(userProject)
                        .vendorProject(vendorProject)
                        .tag("") // Need to discuss
                        .createdAt(Instant.now())
                        .modifiedAt(Instant.now())
                        .build();

                // Save ProjectMappings
                projectMappingsRepository.save(projectMapping);
            } else {
                throw new RuntimeException("Failed to submit to FAST API");
            }
        } else {
            // Create User Project Flow
        }
    }
}