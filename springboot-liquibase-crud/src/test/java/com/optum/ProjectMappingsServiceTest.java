package com.optum;

import com.optum.dto.ProjectMappings;
import com.optum.repository.ProjectMappingsRepository;
import com.optum.service.ProjectMappingsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the ProjectMappingsService class.
 */
@ExtendWith(MockitoExtension.class)
class ProjectMappingsServiceTest {

    @Mock
    private ProjectMappingsRepository repository;

    @InjectMocks
    private ProjectMappingsService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        ProjectMappings mappings = new ProjectMappings();
        when(repository.save(mappings)).thenReturn(mappings);

        ProjectMappings result = service.create(mappings);

        assertNotNull(result);
        verify(repository).save(mappings);
    }

    @Test
    void testGetAll() {
        List<ProjectMappings> mappingsList = List.of(new ProjectMappings());
        when(repository.findAll()).thenReturn(mappingsList);

        List<ProjectMappings> result = service.getAll();

        assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    void testGetById_found() {
        ProjectMappings mappings = new ProjectMappings();
        when(repository.findById("test345")).thenReturn(Optional.of(mappings));

        Optional<ProjectMappings> result = service.getById("test345");

        assertTrue(result.isPresent());
        verify(repository).findById("test345");
    }

    @Test
    void testGetById_notFound() {
        when(repository.findById("test345")).thenReturn(Optional.empty());

        Optional<ProjectMappings> result = service.getById("test345");

        assertFalse(result.isPresent());
        verify(repository).findById("test345");
    }

    @Test
    void testUpdate_success() {
        ProjectMappings existing = new ProjectMappings();
        ProjectMappings updated = new ProjectMappings();
        when(repository.findById("test345")).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        ProjectMappings result = service.update("test345", updated);

        assertNotNull(result);
        verify(repository).findById("test345");
        verify(repository).save(existing);
    }

    @Test
    void testUpdate_notFound() {
        when(repository.findById("test345")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.update("test345", new ProjectMappings());
        });

        assertEquals("ProjectMappings not found with id: test345", exception.getMessage());
        verify(repository).findById("test345");
    }

    @Test
    void testDelete() {
        doNothing().when(repository).deleteById("test345");

        service.delete("test345");

        verify(repository).deleteById("test345");
    }
}