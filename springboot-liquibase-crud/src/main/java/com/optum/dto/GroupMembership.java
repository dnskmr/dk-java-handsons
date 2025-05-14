package com.optum.dto;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "GroupMembership")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupMembership {

    @Id
    @Column(name = "GroupMembershipId", nullable = false, length = 255)
    private String groupMembershipId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserGroupId", referencedColumnName = "UserGroupId")
    private UserGroup userGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"UserId\"", referencedColumnName = "\"UserId\"")
    private User user;

    @Column(name = "CreatedAt")
    private Instant createdAt = Instant.now();

    @Column(name = "ModifiedAt")
    private Instant modifiedAt = Instant.now();
}
