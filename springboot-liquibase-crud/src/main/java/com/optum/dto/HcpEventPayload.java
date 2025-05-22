package com.optum.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class HcpEventPayload {

    private String referenceId;
    private String resourceType;
    private String eventType;
    private ResourceDefinition resourceDefinition;

    @Data
    public static class ResourceDefinition {

        private String id;
        @JsonProperty("ugisProjectId")
        private String uaisProjectId;
        private String projectName;
        private String dataType;
        private LifeCycleInfo lifeCycleInfo;
        private String creationType;
        private Instant startDate;
        private Instant endDate;
        private List<UserRef> users;
        private UserRef createdBy;
    }

    @Data
    public static class LifeCycleInfo {

        private Created created;
    }

    @Data
    public static class Created {

        private Instant eventTime;
        private String actor;
        private String msId;
        private String lifeCycleEventStatus;
    }

    @Data
    public static class UserRef {
        private String id;
    }
}
