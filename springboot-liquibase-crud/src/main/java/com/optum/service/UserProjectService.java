package com.optum.service;

import com.optum.dto.UserProject;
import com.optum.repository.UserProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserProjectService {

    @Autowired
    private UserProjectRepository repository;

    public UserProject create(UserProject userProject) {
        return repository.save(userProject);
    }

    public List<UserProject> getAll() {
        return repository.findAll();
    }

    public Optional<UserProject> getById(String id) {
        return repository.findById(id);
    }

    public UserProject update(String id, UserProject updated) {
        return repository.findById(id).map(existing -> {
            existing.setName(updated.getName());
            existing.setDescription(updated.getDescription());
//            existing.setType(updated.getType());
//            existing.setStatus(updated.getStatus());
            existing.setModifiedAt(updated.getModifiedAt());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("UserProject not found with id: " + id));
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}

