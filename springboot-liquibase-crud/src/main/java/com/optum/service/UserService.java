package com.optum.service;

import com.optum.dto.GroupMembership;
import com.optum.dto.Users;
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

    public Users create(Users users) {
        users.setRole(normalizeRole(users.getRole()));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.setCreatedAt(Instant.now());
        users.setModifiedAt(Instant.now());
        return repository.save(users);
    }

    public List<Users> getAll() {
        return repository.findAll();
    }

    public Optional<Users> getById(String id) {
        return repository.findById(id);
    }

    public Users update(String id, Users updated) {
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
        Users users = repository.findByEmail(emailId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<GroupMembership> memberships = groupMembershipRepository.findByUser_id(users.getUserId());

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
        response.setUserId(users.getUserId());
        response.setUserName(users.getUserName());
        response.setEmail(users.getEmail());
        response.setWorkspace(users.getWorkspace());
        response.setUserGroups(userGroupInfos);

        return response;
    }
}
