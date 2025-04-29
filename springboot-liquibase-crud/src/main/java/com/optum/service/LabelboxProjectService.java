package com.optum.service;

import com.optum.dto.LabelboxProject;
import com.optum.repository.LabelboxProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LabelboxProjectService {

    @Autowired
    private LabelboxProjectRepository repository;

    public LabelboxProject create(LabelboxProject labelboxProject) {
        return repository.save(labelboxProject);
    }

    public List<LabelboxProject> getAll() {
        return repository.findAll();
    }

    public Optional<LabelboxProject> getById(String id) {
        return repository.findById(id);
    }

    public LabelboxProject update(String id, LabelboxProject updated) {
        return repository.findById(id).map(existing -> {
            existing.setUserProject(updated.getUserProject());
            existing.setLbProjectId(updated.getLbProjectId());
            existing.setModifiedAt(updated.getModifiedAt());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("LabelboxProject not found with id: " + id));
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
