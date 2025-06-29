package com.optum;

import com.optum.dto.*;
import com.optum.repository.GroupMembershipRepository;
import com.optum.repository.UserGroupRepository;
import com.optum.repository.UserRepository;
import com.optum.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GroupMembershipRepository groupMembershipRepository;

    @Mock
    private UserGroupRepository userGroupRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void testCreate_success() {
        // Arrange
        Users user = new Users();
        user.setUserName("John Doe");
        user.setPassword("password");
        user.setRole("admin");

        Users savedUser = new Users();
        savedUser.setUserName("John Doe");
        savedUser.setPassword("encodedPassword");
        savedUser.setRole("ROLE_ADMIN");
        savedUser.setCreatedAt(Instant.now());
        savedUser.setModifiedAt(Instant.now());

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenReturn(savedUser);

        // Act
        Users result = userService.create(user);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("John Doe", result.getUserName());
        Assertions.assertEquals("ROLE_ADMIN", result.getRole());
        verify(userRepository).save(any());
    }

    @Test
    void testGetAll_success() {
        // Arrange
        List<Users> users = List.of(new Users());
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<Users> result = userService.getAll();

        // Assert
        Assertions.assertEquals(1, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void testGetById_found() {
        // Arrange
        Users user = new Users();
        user.setUserId("user123");
        when(userRepository.findById("user123")).thenReturn(Optional.of(user));

        // Act
        Optional<Users> result = userService.getById("user123");

        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("user123", result.get().getUserId());
        verify(userRepository).findById("user123");
    }

    @Test
    void testGetById_notFound() {
        // Arrange
        when(userRepository.findById("user123")).thenReturn(Optional.empty());

        // Act
        Optional<Users> result = userService.getById("user123");

        // Assert
        Assertions.assertFalse(result.isPresent());
        verify(userRepository).findById("user123");
    }

    @Test
    void testUpdate_success() {
        // Arrange
        Users existingUser = new Users();
        existingUser.setUserId("user123");
        existingUser.setPassword("oldPassword");

        Users updatedUser = new Users();
        updatedUser.setUserName("Updated Name");
        updatedUser.setPassword("newPassword");

        Users savedUser = new Users();
        savedUser.setUserName("Updated Name");
        savedUser.setPassword("encodedPassword");

        when(userRepository.findById("user123")).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenReturn(savedUser);

        // Act
        Users result = userService.update("user123", updatedUser);

        // Assert
        Assertions.assertEquals("Updated Name", result.getUserName());
        Assertions.assertEquals("encodedPassword", result.getPassword());
        verify(userRepository).findById("user123");
        verify(userRepository).save(any());
    }

    @Test
    void testUpdate_notFound() {
        // Arrange
        when(userRepository.findById("user123")).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            userService.update("user123", new Users());
        });
        Assertions.assertEquals("User not found with id: user123", exception.getMessage());
        verify(userRepository).findById("user123");
    }

    @Test
    void testDelete_success() {
        // Act
        userService.delete("user123");

        // Assert
        verify(userRepository).deleteById("user123");
    }

    @Test
    void testGetUserInfoByEmail_success() {
        // Arrange
        String emailId = "john.doe@example.com";

        Users user = new Users();
        user.setUserId("user123");
        user.setUserName("John Doe");
        user.setEmail(emailId);

        GroupMembership membership = new GroupMembership();
        membership.setUser(user);
        UserGroup group = new UserGroup();
        group.setUserGroupId("group123");
        group.setUserGroupName("Admin Group");
        membership.setUserGroup(group);

        when(userRepository.findByEmail(emailId)).thenReturn(Optional.of(user));
        when(groupMembershipRepository.findByUser_id("user123")).thenReturn(List.of(membership));
        when(userGroupRepository.findById("group123")).thenReturn(Optional.of(group));

        // Act
        UserInfoResponse response = userService.getUserInfoByEmail(emailId);

        // Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals("user123", response.getUserId());
        Assertions.assertEquals("John Doe", response.getUserName());
        Assertions.assertEquals(1, response.getUserGroups().size());
        Assertions.assertEquals("Admin Group", response.getUserGroups().get(0).getUserGroupName());
        verify(userRepository).findByEmail(emailId);
        verify(groupMembershipRepository).findByUser_id("user123");
        verify(userGroupRepository).findById("group123");
    }

    @Test
    void testGetUserInfoByEmail_userNotFound() {
        // Arrange
        String emailId = "unknown@example.com";
        when(userRepository.findByEmail(emailId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            userService.getUserInfoByEmail(emailId);
        });
        Assertions.assertEquals("User not found", exception.getMessage());
        verify(userRepository).findByEmail(emailId);
    }
}