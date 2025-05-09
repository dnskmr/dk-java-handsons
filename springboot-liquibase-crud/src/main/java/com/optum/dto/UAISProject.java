package com.optum.dto;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.time.Instant;
import java.util.Map;

@Entity
@Table(name = "UAISProject")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UAISProject {

    @Id
    @Column(name = "UAISProjectId", nullable = false, length = 255)
    private String uaisProjectId;

    @Column(name = "ProjectName", length = 255)
    private String projectName;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "AIDE", length = 100)
    private String aide;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WorkspaceID", referencedColumnName = "WorkspaceId")
    private Workspace workspace;

    @Type(JsonType.class)
    @Column(name = "Metadata", columnDefinition = "jsonb")
    private Map<String, Object> metadata;

    @Column(name = "CreatedAt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt;

    @Column(name = "ModifiedAt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant modifiedAt;
}
