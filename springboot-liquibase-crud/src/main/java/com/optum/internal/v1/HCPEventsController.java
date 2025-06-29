package com.optum.internal.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.optum.dto.DataAnnotationServiceDocument;
import com.optum.dto.DataAnnotationServiceResourceModel;
import com.optum.service.LabelboxProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/internal/v1/annotation-event")
public class HCPEventsController {

    @Autowired
    private LabelboxProjectService labelboxService;

    /**
     *
     * @param hcpResourceProcessRequest
     * @return
     */
    @PutMapping
    public ResponseEntity create(@RequestBody HcpResourceProcessRequest hcpResourceProcessRequest){
       new Gson().toJson(hcpResourceProcessRequest).replace("\n", "").replace("\r", "");
       ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
       DataAnnotationServiceResourceModel resourceModel = mapper.convertValue(hcpResourceProcessRequest.getResourceDefinition(), DataAnnotationServiceResourceModel.class);
       DataAnnotationServiceDocument dataAnnotationServiceDocument = resourceModel.getDataAnnotationRequestModel();
        // Do the create event operation
        if ("Create".equalsIgnoreCase(dataAnnotationServiceDocument.getCreationType().toString())) {
            labelboxService.handleCreateEvent(dataAnnotationServiceDocument);
        }// Do the update event operation
        else if ("Update".equalsIgnoreCase(dataAnnotationServiceDocument.getCreationType().toString())) {
            labelboxService.handleUpdateEvent(dataAnnotationServiceDocument);
        } else {
            return ResponseEntity.badRequest().body("Unsupported event type");
        }

       return ResponseEntity.ok(dataAnnotationServiceDocument);

    }
}
