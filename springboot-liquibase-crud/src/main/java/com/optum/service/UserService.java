package com.optum.service;

import com.optum.dto.GroupMembership;
import com.optum.dto.User;
import com.optum.dto.UserGroup;
import com.optum.dto.UserInfoResponse;
import com.optum.repository.GroupMembershipRepository;
import com.optum.repository.UserGroupRepository;
import com.optum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private GroupMembershipRepository groupMembershipRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User create(User user) {
        user.setRole(normalizeRole(user.getRole()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(Instant.now());
        user.setModifiedAt(Instant.now());
        return repository.save(user);
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public Optional<User> getById(String id) {
        return repository.findById(id);
    }

    public User update(String id, User updated) {
        return repository.findById(id).map(existing -> {
            existing.setWorkspace(updated.getWorkspace());
            existing.setUserName(updated.getUserName());
            existing.setEmail(updated.getEmail());

            if (updated.getRole() != null && !updated.getRole().isEmpty()) {
                existing.setRole(normalizeRole(updated.getRole()));
            }

            if (updated.getPassword() != null && !updated.getPassword().isEmpty()) {
                existing.setPassword(passwordEncoder.encode(updated.getPassword()));
            }

            existing.setStatus(updated.getStatus());
            existing.setModifiedAt(Instant.now());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    private String normalizeRole(String role) {
        if (role == null) return null;
        return role.startsWith("ROLE_") ? role : "ROLE_" + role.toUpperCase();
    }

    public UserInfoResponse getUserInfoByEmail(String emailId) {
        User user = repository.findByEmail(emailId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<GroupMembership> memberships = groupMembershipRepository.findByUser_UserId(user.getUserId());

        List<UserInfoResponse.UserGroupInfo> userGroupInfos = new ArrayList<>();

        for (GroupMembership membership : memberships) {
            UserGroup group = userGroupRepository.findById(membership.getUserGroup().getUserGroupId())
                    .orElseThrow(() -> new RuntimeException("UserGroup not found"));

            UserInfoResponse.UserGroupInfo groupInfo = new UserInfoResponse.UserGroupInfo();
            groupInfo.setUserGroupId(group.getUserGroupId());
            groupInfo.setUserGroupName(group.getUserGroupName());
            groupInfo.setUserProject(group.getUserProject());  // Assuming this is a List<UserProject>
            groupInfo.setVendorProject(group.getVendorProject());  // Assuming this is a List<VendorProject>

            userGroupInfos.add(groupInfo);
        }

        UserInfoResponse response = new UserInfoResponse();
        response.setUserId(user.getUserId());
        response.setUserName(user.getUserName());
        response.setEmail(user.getEmail());
        response.setWorkspace(user.getWorkspace());
        response.setUserGroups(userGroupInfos);

        return response;
    }
}
