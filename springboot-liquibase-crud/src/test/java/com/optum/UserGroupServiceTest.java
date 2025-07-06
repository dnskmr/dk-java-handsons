package com.optum;

import com.optum.dto.UserGroup;
import com.optum.repository.UserGroupRepository;
import com.optum.service.UserGroupService;
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
 * Unit tests for the UserGroupService class.
 */
class UserGroupServiceTest {

    @Mock
    private UserGroupRepository repository;

    @InjectMocks
    private UserGroupService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        UserGroup userGroup = new UserGroup();
        userGroup.setUserGroupName("Demo Group");

        when(repository.save(userGroup)).thenReturn(userGroup);

        UserGroup result = service.create(userGroup);

        assertNotNull(result);
        assertEquals("Demo Group", result.getUserGroupName());
        verify(repository, times(1)).save(userGroup);
    }

    @Test
    void testGetAll() {
        List<UserGroup> userGroups = Arrays.asList(new UserGroup(), new UserGroup());
        when(repository.findAll()).thenReturn(userGroups);

        List<UserGroup> result = service.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetById_Found() {
        UserGroup userGroup = new UserGroup();
        userGroup.setUserGroupName("Demo Group");

        when(repository.findById("1")).thenReturn(Optional.of(userGroup));

        Optional<UserGroup> result = service.getById("1");

        assertTrue(result.isPresent());
        assertEquals("Demo Group", result.get().getUserGroupName());
        verify(repository, times(1)).findById("1");
    }

    @Test
    void testGetById_NotFound() {
        when(repository.findById("1")).thenReturn(Optional.empty());

        Optional<UserGroup> result = service.getById("1");

        assertFalse(result.isPresent());
        verify(repository, times(1)).findById("1");
    }

    @Test
    void testUpdate_Success() {
        UserGroup existing = new UserGroup();
        existing.setUserGroupName("Old Name");

        UserGroup updated = new UserGroup();
        updated.setUserGroupName("New Name");
        updated.setModifiedAt(Instant.now());

        when(repository.findById("1")).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        UserGroup result = service.update("1", updated);

        assertNotNull(result);
        assertEquals("New Name", result.getUserGroupName());
        verify(repository, times(1)).findById("1");
        verify(repository, times(1)).save(existing);
    }

    @Test
    void testUpdate_NotFound() {
        UserGroup updated = new UserGroup();
        updated.setUserGroupName("New Name");

        when(repository.findById("1")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.update("1", updated));

        assertEquals("UserGroup not found with id: 1", exception.getMessage());
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