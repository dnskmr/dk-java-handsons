package com.optum.dto;

import liquibase.structure.core.DataType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class DataAnnotationServiceDocument {

    private String id;
    private String uaisProjectId;
    private String projectName;
    private DataType dataType;
    private HcpEventPayload.LifeCycleInfo lifeCycleInfo;
    private CreationType creationType;
    private Date startDate;
    private Date endDate;
    private List<UserDocument> users;
    private UserDocument createdBy;
}
