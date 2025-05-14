package com.optum.repository;

import com.optum.dto.GroupMembership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMembershipRepository extends JpaRepository<GroupMembership, String> {
}
