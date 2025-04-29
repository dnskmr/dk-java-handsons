package com.optum.dto;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "ProjectMappings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectMappings {

    @Id
    @Column(name = "ProjectMappingId", nullable = false, length = 255)
    private String projectMappingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserProjectId", referencedColumnName = "UserProjectId")
    private UserProject userProject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VendorProjectId", referencedColumnName = "VendorProjectId")
    private VendorProject vendorProject;

    @Column(name = "Tag", length = 100)
    private String tag;

    @Column(name = "Metadata", columnDefinition = "jsonb")
    private String metadata;

    @Column(name = "CreatedAt")
    private Instant createdAt = Instant.now();

    @Column(name = "ModifiedAt")
    private Instant modifiedAt = Instant.now();
}
