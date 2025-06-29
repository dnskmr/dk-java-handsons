package com.optum;

import com.optum.consumer.FastAPIClient;
import com.optum.dto.*;
import com.optum.repository.*;
import com.optum.service.LabelboxProjectService;
import liquibase.structure.core.DataType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

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
    @Mock
    private VendorProjectRepository vendorProjectRepository;
    @Mock
    private ProjectMappingsRepository projectMappingsRepository;

    @InjectMocks
    private LabelboxProjectService labelboxProjectService;

    private LabelboxProject buildLabelboxProject() {
        return LabelboxProject.builder()
                .labelboxProjectId("lb-123")
                .projectName("Test Project")
                .dataType("Image")
                .workspaceId("workspace-001")
                .build();
    }

    private DataAnnotationServiceDocument buildTestPayload() {
        return new DataAnnotationServiceDocument(
                "doc-001",
                "uais-123",
                "Test Project",
                new DataType("Image"),
                null,
                new CreationType(),
                new Date(),
                new Date(),
                null,
                null
        );
    }

    @Test
    void testCreate_success() {
        // Arrange
        LabelboxProject project = buildLabelboxProject();
        Mockito.when(labelboxProjectRepository.save(Mockito.any())).thenReturn(project);

        // Act
        LabelboxProject result = labelboxProjectService.create(project);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Test Project", result.getProjectName());
        Mockito.verify(labelboxProjectRepository).save(project);
    }

    @Test
    void testGetAll_success() {
        // Arrange
        List<LabelboxProject> projects = List.of(buildLabelboxProject());
        Mockito.when(labelboxProjectRepository.findAll()).thenReturn(projects);

        // Act
        List<LabelboxProject> result = labelboxProjectService.getAll();

        // Assert
        Assertions.assertEquals(1, result.size());
        Mockito.verify(labelboxProjectRepository).findAll();
    }

    @Test
    void testGetById_found() {
        // Arrange
        LabelboxProject project = buildLabelboxProject();
        Mockito.when(labelboxProjectRepository.findById("lb-123")).thenReturn(Optional.of(project));

        // Act
        Optional<LabelboxProject> result = labelboxProjectService.getById("lb-123");

        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("Test Project", result.get().getProjectName());
        Mockito.verify(labelboxProjectRepository).findById("lb-123");
    }

    @Test
    void testGetById_notFound() {
        // Arrange
        Mockito.when(labelboxProjectRepository.findById("lb-123")).thenReturn(Optional.empty());

        // Act
        Optional<LabelboxProject> result = labelboxProjectService.getById("lb-123");

        // Assert
        Assertions.assertFalse(result.isPresent());
        Mockito.verify(labelboxProjectRepository).findById("lb-123");
    }

    @Test
    void testUpdate_success() {
        // Arrange
        LabelboxProject existing = buildLabelboxProject();
        existing.setModifiedAt(null);
        LabelboxProject updated = buildLabelboxProject();
        updated.setModifiedAt(new Date().toInstant());
        Mockito.when(labelboxProjectRepository.findById("lb-123")).thenReturn(Optional.of(existing));
        Mockito.when(labelboxProjectRepository.save(Mockito.any())).thenReturn(updated);

        // Act
        LabelboxProject result = labelboxProjectService.update("lb-123", updated);

        // Assert
        Assertions.assertNotNull(result.getModifiedAt());
        Mockito.verify(labelboxProjectRepository).findById("lb-123");
        Mockito.verify(labelboxProjectRepository).save(updated);
    }

    @Test
    void testUpdate_notFound() {
        // Arrange
        Mockito.when(labelboxProjectRepository.findById("lb-123")).thenReturn(Optional.empty());

        // Act + Assert
        Assertions.assertThrows(RuntimeException.class, () -> {
            labelboxProjectService.update("lb-123", buildLabelboxProject());
        });
    }

    @Test
    void testDelete_success() {
        // Act
        labelboxProjectService.delete("lb-123");

        // Assert
        Mockito.verify(labelboxProjectRepository).deleteById("lb-123");
    }

    @Test
    void testHandleCreateEvent_success() {
        // Arrange
        DataAnnotationServiceDocument payload = buildTestPayload();
        UAISProject uaisProject = new UAISProject();
        uaisProject.setUaisProjectId("uais-123");
        uaisProject.setWorkspace(new Workspace());
        Mockito.when(uaisProjectRepository.findById("uais-123")).thenReturn(Optional.of(uaisProject));

        UserProject userProject = UserProject.builder()
                .userProjectId("user-123")
                .name("Test User Project")
                .build();
        Mockito.when(userProjectRepository.findById("uais-123")).thenReturn(Optional.of(userProject));

        Mockito.when(fastApiClient.createLabelboxProject(Mockito.any())).thenReturn("lb-789");

        // Act
        labelboxProjectService.handleCreateEvent(payload);

        // Assert
        Mockito.verify(labelboxProjectRepository).save(Mockito.any());
        Mockito.verify(vendorProjectRepository).save(Mockito.any());
        Mockito.verify(projectMappingsRepository).save(Mockito.any());
    }

    @Test
    void testHandleUpdateEvent_success() {
        // Arrange
        DataAnnotationServiceDocument payload = buildTestPayload();
        UAISProject existing = new UAISProject();
        existing.setUaisProjectId("uais-123");
        existing.setProjectName("Old Name");
        Mockito.when(uaisProjectRepository.findById("uais-123")).thenReturn(Optional.of(existing));

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
        DataAnnotationServiceDocument payload = buildTestPayload();
        Mockito.when(uaisProjectRepository.findById("uais-123")).thenReturn(Optional.empty());

        // Act + Assert
        Assertions.assertThrows(RuntimeException.class, () -> {
            labelboxProjectService.handleUpdateEvent(payload);
        });
    }
}