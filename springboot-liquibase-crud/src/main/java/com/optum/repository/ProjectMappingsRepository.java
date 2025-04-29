package com.optum.repository;

import com.optum.dto.ProjectMappings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMappingsRepository extends JpaRepository<ProjectMappings, String> {
}
