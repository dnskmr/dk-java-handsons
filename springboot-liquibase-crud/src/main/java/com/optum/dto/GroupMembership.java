package com.optum.dto;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "group_memberships")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupMembership {

    @Id
    @Column(name = "id", nullable = false, length = 255)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user _group_id", referencedColumnName = "id")
    private UserGroup userGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt = Instant.now();

    public void generateId() {
        if (this.id == null || this.id.isEmpty()) {
        }
        this.id = UUID.randomUUID().toString();

    }
}