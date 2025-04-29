package com.optum.repository;

import com.optum.dto.VendorProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorProjectRepository extends JpaRepository<VendorProject, String> {
}