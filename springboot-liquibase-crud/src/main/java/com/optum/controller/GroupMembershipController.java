package com.optum.controller;

import com.optum.dto.GroupMembership;
import com.optum.service.GroupMembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group-memberships")
@RequiredArgsConstructor
public class GroupMembershipController {

    @Autowired
    private GroupMembershipService service;

    @PostMapping
    public ResponseEntity<GroupMembership> create(@RequestBody GroupMembership groupMembership) {
        return ResponseEntity.ok(service.create(groupMembership));
    }

    @GetMapping
    public ResponseEntity<List<GroupMembership>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupMembership> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupMembership> update(@PathVariable String id, @RequestBody GroupMembership updated) {
        return ResponseEntity.ok(service.update(id, updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
