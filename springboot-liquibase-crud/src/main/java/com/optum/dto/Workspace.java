package com.optum.dto;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "Workspace")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Workspace {

    @Id
    @Column(name = "WorkspaceId", nullable = false, length = 255)
    private String workspaceId;

    @Column(name = "WorkspaceName", length = 255)
    private String workspaceName;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "Status", length = 50)
    private String status;

    @Column(name = "CreatedAt")
    private Instant createdAt = Instant.now();

    @Column(name = "ModifiedAt")
    private Instant modifiedAt = Instant.now();
}
