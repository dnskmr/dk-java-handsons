package com.optum;

import com.optum.dto.*;
import com.optum.repository.GroupMembershipRepository;
import com.optum.repository.UserGroupRepository;
import com.optum.repository.UserRepository;
import com.optum.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    void testGetUserInfoByEmail() {
        // Sample input
        String emailId = "john.doe@example.com";

        // Mock User
        User mockUser = new User();
        mockUser.setUserId("user123");
        mockUser.setUserName("John Doere");
        mockUser.setEmail(emailId);
        // set workspace etc.

        // Mock GroupMembership
        GroupMembership gm = new GroupMembership();
        User user = new User();
        user.setUserId("user123");
        gm.setUser(user);
        UserGroup userGroup = new UserGroup();
        userGroup.setUserGroupId("ug001");
        gm.setUserGroup(userGroup);

        // Mock UserGroup
        UserGroup userGroupObj = new UserGroup();
        userGroup.setUserGroupId("ug001");
        userGroup.setUserGroupName("Admin Group");

        // Mock Projects
        UserProject up = new UserProject();
        up.setUserProjectId("up001");
        up.setName("Internal Dashboard Testing");

        VendorProject vp = new VendorProject();
        vp.setVendorProjectId("vp001");
        vp.setProjectName("Third-Party Analytics");

        userGroup.setUserProject(up);
        userGroup.setVendorProject(vp);

        // Mock returns
        when(userRepository.findByEmail(emailId)).thenReturn(Optional.of(mockUser));
        when(groupMembershipRepository.findByUser_UserId("user123")).thenReturn(List.of(gm));
        when(userGroupRepository.findById("ug001")).thenReturn(Optional.of(userGroup));

        // Execute service
        UserInfoResponse response = userService.getUserInfoByEmail(emailId);

        // Assert
        assertNotNull(response);
        assertEquals("user123", response.getUserId());
        assertEquals("John Doere", response.getUserName());
        assertEquals(1, response.getUserGroups().size());
        assertEquals("Admin Group", response.getUserGroups().get(0).getUserGroupName());
    }

    @Test
    void testGetUserInfoByEmail_UserNotFound_ShouldThrowException() {
        // Arrange
        String emailId = "abc@example.com";

        // Mock behavior - user not found
        when(userRepository.findByEmail(emailId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserInfoByEmail(emailId);
        });

        assertEquals("User not found", exception.getMessage());

        // Verify only userRepository was called, others should not be hit
        verify(userRepository, times(1)).findByEmail(emailId);
        verifyNoInteractions(groupMembershipRepository);
        verifyNoInteractions(userGroupRepository);
    }

}
