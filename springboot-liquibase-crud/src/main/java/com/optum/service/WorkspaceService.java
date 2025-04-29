
package com.optum.service;

import com.optum.dto.Workspace;
import com.optum.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkspaceService {

    @Autowired
    private WorkspaceRepository repository;

    public Workspace create(Workspace workspace) {
        return repository.save(workspace);
    }

    public List<Workspace> getAll() {
        return repository.findAll();
    }

    public Optional<Workspace> getById(String id) {
        return repository.findById(id);
    }

    public Workspace update(String id, Workspace updated) {
        return repository.findById(id).map(existing -> {
            existing.setWorkspaceName(updated.getWorkspaceName());
            existing.setDescription(updated.getDescription());
            existing.setStatus(updated.getStatus());
            existing.setModifiedAt(updated.getModifiedAt());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Workspace not found with id: " + id));
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
