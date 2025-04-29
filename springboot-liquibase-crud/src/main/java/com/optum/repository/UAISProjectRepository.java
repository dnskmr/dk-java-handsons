package com.optum.repository;

import com.optum.dto.UAISProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UAISProjectRepository extends JpaRepository<UAISProject, String> {
}