package com.optum;

import com.optum.dto.Workspace;
import com.optum.repository.WorkspaceRepository;
import com.optum.service.WorkspaceService;
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
 * Unit tests for the WorkspaceService class.
 */
public class WorkspaceServiceTest {

    @Mock
    private WorkspaceRepository repository;

    @InjectMocks
    private WorkspaceService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        Workspace workspace = new Workspace();
        workspace.setWorkspaceName("Default Workspace");

        when(repository.save(workspace)).thenReturn(workspace);

        Workspace result = service.create(workspace);

        assertNotNull(result);
        assertEquals("Default Workspace", result.getWorkspaceName());
        verify(repository, times(1)).save(workspace);
    }

    @Test
    void testGetAll() {
        List<Workspace> workspaces = Arrays.asList(new Workspace(), new Workspace());
        when(repository.findAll()).thenReturn(workspaces);

        List<Workspace> result = service.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetById_Found() {
        Workspace workspace = new Workspace();
        workspace.setWorkspaceName("Default Workspace");

        when(repository.findById("1")).thenReturn(Optional.of(workspace));

        Optional<Workspace> result = service.getById("1");

        assertTrue(result.isPresent());
        assertEquals("Default Workspace", result.get().getWorkspaceName());
        verify(repository, times(1)).findById("1");
    }

    @Test
    void testGetById_NotFound() {
        when(repository.findById("1")).thenReturn(Optional.empty());

        Optional<Workspace> result = service.getById("1");

        assertFalse(result.isPresent());
        verify(repository, times(1)).findById("1");
    }

    @Test
    void testUpdate_Success() {
        Workspace existing = new Workspace();
        existing.setWorkspaceName("Old Name");

        Workspace updated = new Workspace();
        updated.setWorkspaceName("New Name");
        updated.setModifiedAt(Instant.now());

        when(repository.findById("1")).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        Workspace result = service.update("1", updated);

        assertNotNull(result);
        assertEquals("New Name", result.getWorkspaceName());
        verify(repository, times(1)).findById("1");
        verify(repository, times(1)).save(existing);
    }

    @Test
    void testUpdate_NotFound() {
        Workspace updated = new Workspace();
        updated.setWorkspaceName("New Name");

        when(repository.findById("1")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.update("1", updated));

        assertEquals("Workspace not found with id: 1", exception.getMessage());
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