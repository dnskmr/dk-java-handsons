package com.optum.controller;

import com.optum.dto.ProjectMappings;
import com.optum.service.ProjectMappingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project-mappings")
public class ProjectMappingsController {

    @Autowired
    private ProjectMappingsService service;

    @PostMapping
    public ResponseEntity<ProjectMappings> create(@RequestBody ProjectMappings mappings) {
        return ResponseEntity.ok(service.create(mappings));
    }

    @GetMapping
    public ResponseEntity<List<ProjectMappings>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectMappings> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectMappings> update(@PathVariable String id, @RequestBody ProjectMappings updated) {
        return ResponseEntity.ok(service.update(id, updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
