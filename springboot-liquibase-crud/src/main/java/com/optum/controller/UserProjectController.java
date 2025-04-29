package com.optum.controller;

import com.optum.dto.UserProject;
import com.optum.service.UserProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-projects")
public class UserProjectController {

    @Autowired
    private UserProjectService service;

    @PostMapping
    public ResponseEntity<UserProject> create(@RequestBody UserProject userProject) {
        return ResponseEntity.ok(service.create(userProject));
    }

    @GetMapping
    public ResponseEntity<List<UserProject>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProject> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProject> update(@PathVariable String id, @RequestBody UserProject updated) {
        return ResponseEntity.ok(service.update(id, updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
