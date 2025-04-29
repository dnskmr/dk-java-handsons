package com.optum.dto;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "UserProject")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProject {

    @Id
    @Column(name = "UserProjectId", nullable = false, length = 255)
    private String userProjectId;

    @Column(name = "Name", length = 255)
    private String name;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "CreatedAt")
    private Instant createdAt = Instant.now();

    @Column(name = "ModifiedAt")
    private Instant modifiedAt = Instant.now();
}
