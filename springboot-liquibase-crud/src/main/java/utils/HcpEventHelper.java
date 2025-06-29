//package utils;
//
//import com.azure.resourcemanager.compute.models.ExecutionState;
//import com.optum.hcpevents.client.EventsPlatformClient;
//import com.optum.hcpevents.config.EventsPlatformConfiguration;
//import com.optum.hcpevents.model.EventRequest;
//import com.optum.hcpevents.model.EventResponse;
//import com.optum.optumlabs.das.model.HCPResourceProcessRequest;
//import com.optum.optumlabs.das.exception.handler.HCPEventException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpStatus;
//
//import java.net.http.HttpResponse;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//import static com.optum.optumlabs.das.config.HCPClientConfig.HCP_EVENT_CONFIG_MAP_QUALIFIER;
//
//@Configuration
//@Slf4j
//public class HcpEventHelper {
//
//    public static final String ANNOTATION_EVENT_NAME = "annotation_event_type";
//    private static final String SPEC_VERSION = "1.0";
//
//    @Autowired
//    @Qualifier(HCP_EVENT_CONFIG_MAP_QUALIFIER)
//    private Map<String, EventsPlatformConfiguration> eventsPlatformConfiguration;
//
//    public HcpEventHelper(Map<String, EventsPlatformConfiguration> eventsPlatformConfiguration) {
//        this.eventsPlatformConfiguration = eventsPlatformConfiguration;
//    }
//
//    /**
//     * Function to send resource create request to an HCP event.
//     */
//    public void sendAnnotationCreateRequestToHCPEvent(HCPResourceProcessRequest request) {
//        String sanitizedReferenceId = request.getReferenceId()
//                .replaceAll("[\\r\\n]", "")
//                .replaceAll("[^\\w\\s]", "");
//
//        log.info("Entered sendAnnotationCreateRequestToHCPEvent method for resource create request for resource ID: {}", sanitizedReferenceId);
//
//        String responseMessage;
//        ExecutionState executionState;
//
//        try {
//            EventsPlatformConfiguration hcpConfiguration = eventsPlatformConfiguration.get(ANNOTATION_EVENT_NAME);
//
//            EventRequest<HCPResourceProcessRequest> eventRequest = EventRequest.<HCPResourceProcessRequest>builder(hcpConfiguration)
//                    .id(UUID.randomUUID().toString())
//                    .source(hcpConfiguration.getSource())
//                    .subject(hcpConfiguration.getSubject())
//                    .environment(hcpConfiguration.getEnvironment())
//                    .type(hcpConfiguration.getEventType())
//                    .specVersion(SPEC_VERSION)
//                    .dataContentType("application/json")
//                    .data(request)
//                    .build();
//
//            EventsPlatformClient eventsPlatformClient = new EventsPlatformClient(hcpConfiguration);
//            HttpResponse<List<EventResponse>> eventsResponse = eventsPlatformClient.publishEvents(List.of(eventRequest));
//
//            if (eventsResponse.statusCode() == 201) {
//                executionState = ExecutionState.SUCCEEDED;
//                responseMessage = "Event publishing succeeded";
//                log.info("HCP status update event is published successfully.");
//            } else if (eventsResponse.statusCode() == 207) {
//                executionState = ExecutionState.SUCCEEDED;
//                responseMessage = "HCP status update event is partially successful.";
//                log.info("Response: {}", responseMessage);
//            } else if (eventsResponse.statusCode() == 401 || eventsResponse.statusCode() == 403 || eventsResponse.statusCode() == 504) {
//                executionState = ExecutionState.FAILED;
//                responseMessage = "Authorization error";
//                log.error("Response: {}", responseMessage);
//            } else {
//                executionState = ExecutionState.FAILED;
//                responseMessage = "Event publishing failed";
//                log.error("Response: {}", responseMessage);
//            }
//        } catch (final Exception exception) {
//            responseMessage = String.format("Error occurred while sending request: %s", exception.getMessage());
//            log.error(responseMessage, exception);
//            throw new HCPEventException(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//        log.info("Publish request completed with status: {} and response: {}", executionState, responseMessage);
//    }
//}