package com.optum.dto;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "LabelboxProject")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LabelboxProject {

    @Id
    @Column(name = "LabelboxProjectId", nullable = false, length = 255)
    private String labelboxProjectId;

    @Column(name = "ProjectName", nullable = true, length = 255)
    private String projectName;

    @Column(name = "DataType", length = 255)
    private String dataType;

    @Column(name = "Tag", length = 255)
    private String tag;

    @Column(name = "Type", length = 255)
    private String type;

    @Column(name = "WorkspaceId", length = 255)
    private String workspaceId;

    @Column(name = "CreatedAt")
    private Instant createdAt = Instant.now();

    @Column(name = "ModifiedAt")
    private Instant modifiedAt = Instant.now();
}
