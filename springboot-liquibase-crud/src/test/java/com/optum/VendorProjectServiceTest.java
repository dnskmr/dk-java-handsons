package com.optum;

import com.optum.dto.VendorProject;
import com.optum.repository.VendorProjectRepository;
import com.optum.service.VendorProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the VendorProjectService class.
 */
public class VendorProjectServiceTest {

    @Mock
    private VendorProjectRepository repository;

    @InjectMocks
    private VendorProjectService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        VendorProject vendorProject = new VendorProject();
        vendorProject.setProjectName("Default Project");

        when(repository.save(vendorProject)).thenReturn(vendorProject);

        VendorProject result = service.create(vendorProject);

        assertNotNull(result);
        assertEquals("Default Project", result.getProjectName());
        verify(repository, times(1)).save(vendorProject);
    }

    @Test
    void testGetAll() {
        List<VendorProject> vendorProjects = Arrays.asList(new VendorProject(), new VendorProject());
        when(repository.findAll()).thenReturn(vendorProjects);

        List<VendorProject> result = service.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetById_Found() {
        VendorProject vendorProject = new VendorProject();
        vendorProject.setProjectName("Default Project");

        when(repository.findById("1")).thenReturn(Optional.of(vendorProject));

        Optional<VendorProject> result = service.getById("1");

        assertTrue(result.isPresent());
        assertEquals("Default Project", result.get().getProjectName());
        verify(repository, times(1)).findById("1");
    }

    @Test
    void testGetById_NotFound() {
        when(repository.findById("1")).thenReturn(Optional.empty());

        Optional<VendorProject> result = service.getById("1");

        assertFalse(result.isPresent());
        verify(repository, times(1)).findById("1");
    }

    @Test
    void testUpdate_Success() {
        VendorProject existing = new VendorProject();
        existing.setProjectName("Old Name");

        VendorProject updated = new VendorProject();
        updated.setProjectName("New Name");
        updated.setModifiedAt(Instant.now());

        when(repository.findById("1")).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        VendorProject result = service.update("1", updated);

        assertNotNull(result);
        assertEquals("New Name", result.getProjectName());
        verify(repository, times(1)).findById("1");
        verify(repository, times(1)).save(existing);
    }

    @Test
    void testUpdate_NotFound() {
        VendorProject updated = new VendorProject();
        updated.setProjectName("New Name");

        when(repository.findById("1")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.update("1", updated));

        assertEquals("VendorProject not found with id: 1", exception.getMessage());
        verify(repository, times(1)).findById("1");
        verify(repository, never()).save(any());
    }

    @Test
    void testDelete() {
        doNothing().when(repository).deleteById("1");

        service.delete("1");

        verify(repository, times(1)).deleteById("1");
    }
}