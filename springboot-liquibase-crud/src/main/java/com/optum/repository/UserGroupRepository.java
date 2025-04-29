package com.optum.repository;

import com.optum.dto.UAISProject;
import com.optum.dto.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupRepository extends JpaRepository<UserGroup, String> {
}
