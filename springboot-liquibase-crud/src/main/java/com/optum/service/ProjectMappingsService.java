package com.optum.service;

import com.optum.dto.ProjectMappings;
import com.optum.repository.ProjectMappingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectMappingsService {

    @Autowired
    private ProjectMappingsRepository repository;

    public ProjectMappings create(ProjectMappings mappings) {
        return repository.save(mappings);
    }

    public List<ProjectMappings> getAll() {
        return repository.findAll();
    }

    public Optional<ProjectMappings> getById(String id) {
        return repository.findById(id);
    }

    public ProjectMappings update(String id, ProjectMappings updated) {
        return repository.findById(id).map(existing -> {
            existing.setUserProject(updated.getUserProject());
            existing.setVendorProject(updated.getVendorProject());
            existing.setTag(updated.getTag());
            existing.setMetadata(updated.getMetadata());
            existing.setModifiedAt(updated.getModifiedAt());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("ProjectMappings not found with id: " + id));
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
