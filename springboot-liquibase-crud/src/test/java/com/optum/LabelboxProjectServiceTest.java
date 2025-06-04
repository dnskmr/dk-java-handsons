package com.optum;

import com.optum.consumer.FastAPIClient;
import com.optum.dto.HcpEventPayload;
import com.optum.dto.UAISProject;
import com.optum.repository.LabelboxProjectRepository;
import com.optum.repository.UAISProjectRepository;
import com.optum.repository.UserProjectRepository;
import com.optum.service.LabelboxProjectService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class LabelboxProjectServiceTest {

    @Mock
    private LabelboxProjectRepository labelboxProjectRepository;
    @Mock
    private UAISProjectRepository uaisProjectRepository;
    @Mock
    private UserProjectRepository userProjectRepository;
    @Mock
    private FastAPIClient fastApiClient;

    @InjectMocks
    private LabelboxProjectService labelboxProjectService;

    private HcpEventPayload buildTestPayload() {
        HcpEventPayload payload = new HcpEventPayload();
        payload.setReferenceId("ref-001");
        payload.setResourceType("PROJECT");
        payload.setEventType("CREATE");

        HcpEventPayload.ResourceDefinition resourceDef = new HcpEventPayload.ResourceDefinition();
        resourceDef.setId("resource-123");
        resourceDef.setUaisProjectId("uais-123");
        resourceDef.setProjectName("Test Project");
        resourceDef.setDataType("image");
        resourceDef.setCreationType("auto");
        resourceDef.setStartDate(Instant.now());
        resourceDef.setEndDate(Instant.now().plusSeconds(3600));

        HcpEventPayload.UserRef createdBy = new HcpEventPayload.UserRef();
        createdBy.setId("aide-456");
        resourceDef.setCreatedBy(createdBy);

        resourceDef.setUsers(List.of(createdBy));

        payload.setResourceDefinition(resourceDef);
        return payload;
    }

    @Test
    void testHandleCreateEvent_success() {
        // Arrange
        HcpEventPayload payload = buildTestPayload();
        Mockito.when(fastApiClient.createLabelboxProject(Mockito.any())).thenReturn("lb-789");

        // Act
        labelboxProjectService.handleCreateEvent(payload);

        // Assert: Verify each save call happened
        Mockito.verify(userProjectRepository).save(Mockito.argThat(up ->
                up.getName().equals("Test Project") &&
                        up.getDescription().contains("HCP")
        ));

        Mockito.verify(uaisProjectRepository).save(Mockito.argThat(uais ->
                uais.getUaisProjectId().equals("uais-123") &&
                        uais.getAide().equals("aide-456") &&
                        uais.getWorkspace() != null
        ));

        Mockito.verify(fastApiClient).createLabelboxProject(Mockito.any());

    }

    @Test
    void testHandleUpdateEvent_success() {
        // Arrange
        HcpEventPayload payload = buildTestPayload();

        UAISProject existing = new UAISProject();
        existing.setUaisProjectId("uais-123");
        existing.setProjectName("Old Name");

        Mockito.when(uaisProjectRepository.findById("uais-123"))
                .thenReturn(Optional.of(existing));

        // Act
        labelboxProjectService.handleUpdateEvent(payload);

        // Assert
        Mockito.verify(uaisProjectRepository).save(Mockito.argThat(up ->
                up.getProjectName().equals("Test Project") &&
                        up.getModifiedAt() != null
        ));
    }

    @Test
    void testHandleUpdateEvent_projectNotFound() {
        // Arrange
        HcpEventPayload payload = buildTestPayload();

        Mockito.when(uaisProjectRepository.findById("uais-123"))
                .thenReturn(Optional.empty());

        // Act + Assert
        Assertions.assertThrows(RuntimeException.class, () -> {
            labelboxProjectService.handleUpdateEvent(payload);
        });
    }
}

