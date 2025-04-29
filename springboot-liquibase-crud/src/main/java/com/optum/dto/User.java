package com.optum.dto;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "User")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "UserId", nullable = false, length = 255)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WorkspaceId", referencedColumnName = "WorkspaceId")
    private Workspace workspace;

    @Column(name = "UserName", length = 255)
    private String userName;

    @Column(name = "Email", length = 255)
    private String email;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "Role", length = 100)
    private String role;

    @Column(name = "Status", length = 100)
    private String status;

    @Column(name = "CreatedAt")
    private Instant createdAt = Instant.now();

    @Column(name = "ModifiedAt")
    private Instant modifiedAt = Instant.now();
}
