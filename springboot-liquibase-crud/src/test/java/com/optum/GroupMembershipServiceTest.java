package com.optum;

import com.optum.dto.GroupMembership;
import com.optum.dto.UserGroup;
import com.optum.dto.Users;
import com.optum.repository.GroupMembershipRepository;
import com.optum.service.GroupMembershipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GroupMembershipServiceTest {

    @Mock
    private GroupMembershipRepository repository;

    @InjectMocks
    private GroupMembershipService service;

    private GroupMembership sampleMembership;
    private Users mockUser;
    private UserGroup mockUserGroup;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = new Users();
        mockUser.setUserId("user123");
        mockUser.setUserName("Test User");

        mockUserGroup = new UserGroup();
        mockUserGroup.setUserGroupId("group456");
        mockUserGroup.setUserGroupName("Test Group");

        sampleMembership = new GroupMembership();
        sampleMembership.setId("1");
        sampleMembership.setUser(mockUser);
        sampleMembership.setUserGroup(mockUserGroup);
        sampleMembership.setCreatedAt(Instant.now());
        sampleMembership.setUpdatedAt(Instant.now());
    }

    @Test
    void testCreate() {
        when(repository.save(any(GroupMembership.class))).thenReturn(sampleMembership);

        GroupMembership result = service.create(sampleMembership);

        assertNotNull(result);
        assertEquals("user123", result.getUser().getUserId());
        assertEquals("group456", result.getUserGroup().getUserGroupId());
        verify(repository, times(1)).save(sampleMembership);
    }

    @Test
    void testGetAll() {
        List<GroupMembership> list = Collections.singletonList(sampleMembership);
        when(repository.findAll()).thenReturn(list);

        List<GroupMembership> result = service.getAll();

        assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    void testGetById() {
        when(repository.findById("1")).thenReturn(Optional.of(sampleMembership));

        Optional<GroupMembership> result = service.getById("1");

        assertTrue(result.isPresent());
        assertEquals("user123", result.get().getUser().getUserId());
        verify(repository).findById("1");
    }

    @Test
    void testUpdate_Success() {
        GroupMembership updated = new GroupMembership();
        Users user = new Users();
        user.setUserId("user768");
        user.setUserName("TestUser");
        updated.setUser(user);
        UserGroup group = new UserGroup();
        group.setUserGroupId("group123");
        group.setUserGroupName("TestGroup");
        updated.setUserGroup(group);
        updated.setCreatedAt(Instant.now());
        updated.setUpdatedAt(Instant.now());

        when(repository.findById("1")).thenReturn(Optional.of(sampleMembership));
        when(repository.save(any(GroupMembership.class))).thenReturn(sampleMembership);

        GroupMembership result = service.update("1", updated);

        assertEquals("user768", sampleMembership.getUser().getUserId());
        assertEquals("group123", sampleMembership.getUserGroup().getUserGroupId());
        verify(repository).findById("1");
        verify(repository).save(sampleMembership);
    }

    @Test
    void testUpdate_NotFound() {
        when(repository.findById("1")).thenReturn(Optional.empty());

        GroupMembership updated = new GroupMembership();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.update("1", updated);
        });

        assertEquals("GroupMembership not found", exception.getMessage());
        verify(repository).findById("1");
        verify(repository, never()).save(any());
    }

    @Test
    void testDelete() {
        doNothing().when(repository).deleteById("1");

        service.delete("1");

        verify(repository, times(1)).deleteById("1");
    }
}
