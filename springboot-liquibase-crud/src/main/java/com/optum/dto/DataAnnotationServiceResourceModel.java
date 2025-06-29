package com.optum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class DataAnnotationServiceResourceModel {

    private String id;
    private String projectId;
    private String referenceId;
    private String aide;
    private String location;
    private String workspaceId;
    private String environmentType;
    private DataAnnotationServiceDocument DataAnnotationRequestModel;
    private String projectName;
    private String description;
    private SourceADgroup adminADgroup;
    private SourceADgroup contributorADgroup;
    private SourceADgroup readADgroup;


}
