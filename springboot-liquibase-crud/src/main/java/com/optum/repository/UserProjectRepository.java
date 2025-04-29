package com.optum.repository;

import com.optum.dto.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProjectRepository extends JpaRepository<UserProject, String> {
}
