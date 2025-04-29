
package com.optum.service;

import com.optum.dto.VendorProject;
import com.optum.repository.VendorProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendorProjectService {

    @Autowired
    private VendorProjectRepository repository;

    public VendorProject create(VendorProject vendorProject) {
        return repository.save(vendorProject);
    }

    public List<VendorProject> getAll() {
        return repository.findAll();
    }

    public Optional<VendorProject> getById(String id) {
        return repository.findById(id);
    }

    public VendorProject update(String id, VendorProject updated) {
        return repository.findById(id).map(existing -> {
            existing.setProjectName(updated.getProjectName());
            existing.setDataType(updated.getDataType());
            existing.setTag(updated.getTag());
            existing.setVendorType(updated.getVendorType());
            existing.setStartDate(updated.getStartDate());
            existing.setEndDate(updated.getEndDate());
            existing.setModifiedAt(updated.getModifiedAt());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("VendorProject not found with id: " + id));
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
