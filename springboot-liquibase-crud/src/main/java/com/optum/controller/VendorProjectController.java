
package com.optum.controller;

import com.optum.dto.VendorProject;
import com.optum.service.VendorProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor-projects")
public class VendorProjectController {

    @Autowired
    private VendorProjectService service;

    @PostMapping
    public ResponseEntity<VendorProject> create(@RequestBody VendorProject vendorProject) {
        return ResponseEntity.ok(service.create(vendorProject));
    }

    @GetMapping
    public ResponseEntity<List<VendorProject>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendorProject> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendorProject> update(@PathVariable String id, @RequestBody VendorProject updated) {
        return ResponseEntity.ok(service.update(id, updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
