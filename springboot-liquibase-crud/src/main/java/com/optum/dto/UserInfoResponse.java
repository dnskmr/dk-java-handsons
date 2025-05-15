package com.optum.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserInfoResponse {
    private String userId;
    private String userName;
    private String email;
    private Workspace workspace;
    private List<UserGroupInfo> userGroups;

    @Data
    public static class UserGroupInfo {
        private String userGroupId;
        private String userGroupName;
        private UserProject userProject;
        private VendorProject vendorProject;
    }
}

