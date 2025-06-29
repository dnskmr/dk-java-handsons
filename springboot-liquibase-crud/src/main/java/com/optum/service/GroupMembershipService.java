package com.optum.service;

import com.optum.dto.GroupMembership;
import com.optum.repository.GroupMembershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupMembershipService {

    @Autowired
    private final GroupMembershipRepository repository;

    public GroupMembership create(GroupMembership groupMembership) {
        return repository.save(groupMembership);
    }

    public List<GroupMembership> getAll() {
        return repository.findAll();
    }

    public Optional<GroupMembership> getById(String id) {
        return repository.findById(id);
    }

    public GroupMembership update(String id, GroupMembership updated) {
        GroupMembership existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("GroupMembership not found"));

        existing.setUser(updated.getUser());
        existing.setUserGroup(updated.getUserGroup());
        existing.setCreatedAt(updated.getCreatedAt());
        existing.setUpdatedAt(updated.getUpdatedAt());

        return repository.save(existing);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
