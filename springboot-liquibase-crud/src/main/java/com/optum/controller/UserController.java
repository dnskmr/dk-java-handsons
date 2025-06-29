package com.optum.controller;

import com.optum.dto.Users;
import com.optum.dto.UserInfoResponse;
import com.optum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<Users> create(@RequestBody Users users) {
        return ResponseEntity.ok(service.create(users));
    }

    @GetMapping
    public ResponseEntity<List<Users>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> update(@PathVariable String id, @RequestBody Users updated) {
        return ResponseEntity.ok(service.update(id, updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/info")
    public ResponseEntity<UserInfoResponse> getUserInfoByEmail(@RequestParam String emailId) {
        return ResponseEntity.ok(service.getUserInfoByEmail(emailId));
    }

}
