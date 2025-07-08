package com.optum;

import com.optum.dto.UAISProject;
import com.optum.repository.UAISProjectRepository;
import com.optum.service.UAISProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the UAISProjectService class.
 */
class UAISProjectServiceTest {

    @Mock
    private UAISProjectRepository repository;

    @InjectMocks
    private UAISProjectService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        UAISProject project = new UAISProject();
        when(repository.save(project)).thenReturn(project);

        UAISProject result = service.create(project);

        assertNotNull(result);
        verify(repository).save(project);
    }

    @Test
    void testGetAll() {
        List<UAISProject> projects = List.of(new UAISProject());
        when(repository.findAll()).thenReturn(projects);

        List<UAISProject> result = service.getAll();

        assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    void testGetById_found() {
        UAISProject project = new UAISProject();
        when(repository.findById("uais123")).thenReturn(Optional.of(project));

        Optional<UAISProject> result = service.getById("uais123");

        assertTrue(result.isPresent());
        verify(repository).findById("uais123");
    }

    @Test
    void testGetById_notFound() {
        when(repository.findById("uais123")).thenReturn(Optional.empty());

        Optional<UAISProject> result = service.getById("uais123");

        assertFalse(result.isPresent());
        verify(repository).findById("uais123");
    }

    @Test
    void testUpdate() {
        UAISProject updated = new UAISProject();
        updated.setUaisProjectId("uais123");
        when(repository.save(updated)).thenReturn(updated);

        UAISProject result = service.update("uais123", updated);

        assertNotNull(result);
        assertEquals("uais123", result.getUaisProjectId());
        verify(repository).save(updated);
    }

    @Test
    void testDelete() {
        doNothing().when(repository).deleteById("uais123");

        service.delete("uais123");

        verify(repository).deleteById("uais123");
    }
}