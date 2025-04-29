
package com.optum.dto;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "UserGroup")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserGroup {

    @Id
    @Column(name = "UserGroupId", nullable = false, length = 255)
    private String userGroupId;

    @Column(name = "UserGroupName", length = 255)
    private String userGroupName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserProjectId", referencedColumnName = "UserProjectId")
    private UserProject userProject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VendorProjectId", referencedColumnName = "VendorProjectId")
    private VendorProject vendorProject;

    @Column(name = "Status", length = 100)
    private String status;

    @Column(name = "Tag", length = 100)
    private String tag;

    @Column(name = "CreatedAt")
    private Instant createdAt = Instant.now();

    @Column(name = "ModifiedAt")
    private Instant modifiedAt = Instant.now();
}

