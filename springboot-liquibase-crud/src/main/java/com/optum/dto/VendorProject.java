
package com.optum.dto;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "VendorProject")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorProject {

    @Id
    @Column(name = "VendorProjectId", nullable = false, length = 255)
    private String vendorProjectId;

    @Column(name = "ProjectName", length = 255)
    private String projectName;

    @Column(name = "DataType", length = 100)
    private String dataType;

    @Column(name = "Tag", length = 100)
    private String tag;

    @Column(name = "VendorType", length = 100)
    private String vendorType;

    @Column(name = "StartDate")
    private Instant startDate;

    @Column(name = "EndDate")
    private Instant endDate;

    @Column(name = "CreatedAt")
    private Instant createdAt = Instant.now();

    @Column(name = "ModifiedAt")
    private Instant modifiedAt = Instant.now();
}

