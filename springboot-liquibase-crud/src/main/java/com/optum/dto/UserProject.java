package com.optum.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "UserProject")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserProject {

    @Id
    @Column(name = "UserProjectId", nullable = false, length = 255)
    private String userProjectId;

    @Column(name = "Name", length = 255)
    private String name;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "Type", columnDefinition = "TEXT")
    private String type;

    @Column(name = "Status", columnDefinition = "TEXT")
    private String status;

    @Column(name = "CreatedAt")
    private Instant createdAt = Instant.now();

    @Column(name = "ModifiedAt")
    private Instant modifiedAt = Instant.now();
}
