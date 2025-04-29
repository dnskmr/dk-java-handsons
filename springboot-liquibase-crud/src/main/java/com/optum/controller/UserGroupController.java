
package com.optum.controller;

import com.optum.dto.UAISProject;
import com.optum.dto.UserGroup;
import com.optum.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-groups")
public class UserGroupController {

    @Autowired
    private UserGroupService service;

    @PostMapping
    public ResponseEntity<UserGroup> create(@RequestBody UserGroup userGroup) {
        return ResponseEntity.ok(service.create(userGroup));
    }

    @GetMapping
    public ResponseEntity<List<UserGroup>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserGroup> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserGroup> update(@PathVariable String id, @RequestBody UserGroup updated) {
        return ResponseEntity.ok(service.update(id, updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

