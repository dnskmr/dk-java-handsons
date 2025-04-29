package com.optum.controller;

import com.optum.dto.UAISProject;
import com.optum.service.UAISProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/uais-projects")
@RequiredArgsConstructor
public class UAISProjectController {

    private final UAISProjectService service;

    @PostMapping
    public ResponseEntity<UAISProject> create(@RequestBody UAISProject uaisProject) {
        return ResponseEntity.ok(service.create(uaisProject));
    }

    @GetMapping
    public ResponseEntity<List<UAISProject>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UAISProject> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UAISProject> update(@PathVariable String id, @RequestBody UAISProject updated) {
        return ResponseEntity.ok(service.update(id, updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
