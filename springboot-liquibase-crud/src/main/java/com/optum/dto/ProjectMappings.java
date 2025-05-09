package com.optum.dto;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.time.Instant;
import java.util.Map;

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

    @Type(JsonType.class)
    @Column(name = "Metadata", columnDefinition = "jsonb")
    private Map<String, Object> metadata;

    @Column(name = "CreatedAt")
    private Instant createdAt = Instant.now();

    @Column(name = "ModifiedAt")
    private Instant modifiedAt = Instant.now();
}
