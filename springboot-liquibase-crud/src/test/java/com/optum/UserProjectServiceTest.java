package com.optum;

import com.optum.dto.UserProject;
import com.optum.repository.UserProjectRepository;
import com.optum.service.UserProjectService;
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
 * Unit tests for the UserProjectService class.
 */
class UserProjectServiceTest {

    @Mock
    private UserProjectRepository repository;

    @InjectMocks
    private UserProjectService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        UserProject userProject = new UserProject();
        userProject.setName("Default Project");

        when(repository.save(userProject)).thenReturn(userProject);

        UserProject result = service.create(userProject);

        assertNotNull(result);
        assertEquals("Default Project", result.getName());
        verify(repository, times(1)).save(userProject);
    }

    @Test
    void testGetAll() {
        List<UserProject> userProjects = Arrays.asList(new UserProject(), new UserProject());
        when(repository.findAll()).thenReturn(userProjects);

        List<UserProject> result = service.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetById_Found() {
        UserProject userProject = new UserProject();
        userProject.setName("Default Project");

        when(repository.findById("1")).thenReturn(Optional.of(userProject));

        Optional<UserProject> result = service.getById("1");

        assertTrue(result.isPresent());
        assertEquals("Default Project", result.get().getName());
        verify(repository, times(1)).findById("1");
    }

    @Test
    void testGetById_NotFound() {
        when(repository.findById("1")).thenReturn(Optional.empty());

        Optional<UserProject> result = service.getById("1");

        assertFalse(result.isPresent());
        verify(repository, times(1)).findById("1");
    }

    @Test
    void testUpdate_Success() {
        UserProject existing = new UserProject();
        existing.setName("Old Name");

        UserProject updated = new UserProject();
        updated.setName("New Name");
        updated.setModifiedAt(Instant.now());

        when(repository.findById("1")).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        UserProject result = service.update("1", updated);

        assertNotNull(result);
        assertEquals("New Name", result.getName());
        verify(repository, times(1)).findById("1");
        verify(repository, times(1)).save(existing);
    }

    @Test
    void testUpdate_NotFound() {
        UserProject updated = new UserProject();
        updated.setName("New Name");

        when(repository.findById("1")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.update("1", updated));

        assertEquals("UserProject not found with id: 1", exception.getMessage());
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