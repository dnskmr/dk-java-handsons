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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserProjectId", referencedColumnName = "UserProjectId")
    private UserProject userProject;

    @Column(name = "LbProjectId", length = 255)
    private String lbProjectId;

    @Column(name = "CreatedAt")
    private Instant createdAt = Instant.now();

    @Column(name = "ModifiedAt")
    private Instant modifiedAt = Instant.now();
}
