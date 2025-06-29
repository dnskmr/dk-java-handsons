package com.optum.repository;

import com.optum.dto.GroupMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GroupMembershipRepository extends JpaRepository<GroupMembership, String> {
    List<GroupMembership> findByUser_UserId(String userId);

    List<GroupMembership> findByUser_id(String userId);
}
