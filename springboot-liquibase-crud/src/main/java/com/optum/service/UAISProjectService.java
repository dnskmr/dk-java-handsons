
package com.optum.service;

import com.optum.dto.UAISProject;
import com.optum.repository.UAISProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UAISProjectService {

    private final UAISProjectRepository repository;

    public UAISProject create(UAISProject uaisProject) {
        return repository.save(uaisProject);
    }

    public List<UAISProject> getAll() {
        return repository.findAll();
    }

    public Optional<UAISProject> getById(String id) {
        return repository.findById(id);
    }

    public UAISProject update(String id, UAISProject updated) {
        updated.setUaisProjectId(id);
        return repository.save(updated);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
