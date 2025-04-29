
package com.optum.service;

import com.optum.dto.UAISProject;
import com.optum.dto.UserGroup;
import com.optum.repository.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserGroupService {

    @Autowired
    private UserGroupRepository repository;

    public UserGroup create(UserGroup userGroup) {
        return repository.save(userGroup);
    }

    public List<UserGroup> getAll() {
        return repository.findAll();
    }

    public Optional<UserGroup> getById(String id) {
        return repository.findById(id);
    }

    public UserGroup update(String id, UserGroup updated) {
        return repository.findById(id).map(existing -> {
            existing.setUserGroupName(updated.getUserGroupName());
            existing.setUserProject(updated.getUserProject());
            existing.setVendorProject(updated.getVendorProject());
            existing.setStatus(updated.getStatus());
            existing.setTag(updated.getTag());
            existing.setModifiedAt(updated.getModifiedAt());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("UserGroup not found with id: " + id));
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
