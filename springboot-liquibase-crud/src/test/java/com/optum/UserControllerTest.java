package com.optum;

import com.optum.controller.UserController;
import com.optum.dto.*;
import com.optum.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    public UserControllerTest() {
        openMocks(this);
    }

    @Test
    void testGetUserInfoByEmail() {
        // Arrange
        String emailId = "john.doe@example.com";

        // Mock Workspace
        Workspace workspace = new Workspace();
        workspace.setWorkspaceId("ws001");
        workspace.setWorkspaceName("Development Workspace");
        workspace.setDescription("Main workspace for backend development");

        // Mock UserProject
        UserProject userProject = new UserProject();
        userProject.setUserProjectId("up001");
        userProject.setName("Internal Dashboard Testing");
        userProject.setDescription("Project for internal tooling and analytics");

        // Mock VendorProject
        VendorProject vendorProject = new VendorProject();
        vendorProject.setVendorProjectId("vp001");
        vendorProject.setProjectName("Third-Party Analytics");
        vendorProject.setDataType("Healthcare");
        vendorProject.setTag("analytics");
        vendorProject.setVendorType("External");

        // Mock UserGroup
        UserGroup userGroup = new UserGroup();
        userGroup.setUserGroupId("ug001");
        userGroup.setUserGroupName("Admin Group");
        userGroup.setUserProject(userProject);
        userGroup.setVendorProject(vendorProject);

        // Build full UserInfoResponse
        UserInfoResponse response = new UserInfoResponse();
        response.setUserId("user123");
        response.setUserName("John Doere");
        response.setEmail(emailId);
        response.setWorkspace(workspace);
        response.setUserGroups(List.of(new UserInfoResponse.UserGroupInfo()));

        when(userService.getUserInfoByEmail(emailId)).thenReturn(response);

        // Act
        ResponseEntity<UserInfoResponse> result = userController.getUserInfoByEmail(emailId);

        // Assert
        assertEquals(200, result.getStatusCodeValue());
        assertEquals("John Doere", result.getBody().getUserName());
        assertEquals("Development Workspace", result.getBody().getWorkspace().getWorkspaceName());
        assertEquals(1, result.getBody().getUserGroups().size());

        verify(userService, times(1)).getUserInfoByEmail(emailId);
    }

    @Test
    void testGetUserInfoByEmail_UserNotFound_ShouldThrowException() {
        // Arrange
        String emailId = "abc@example.com";

        when(userService.getUserInfoByEmail(emailId))
                .thenThrow(new RuntimeException("User not found"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userController.getUserInfoByEmail(emailId);
        });

        assertEquals("User not found", exception.getMessage());

        verify(userService, times(1)).getUserInfoByEmail(emailId);
    }
}
