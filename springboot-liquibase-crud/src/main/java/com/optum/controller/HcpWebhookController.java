package com.optum.controller;

import com.optum.dto.HcpEventPayload;
import com.optum.service.LabelboxProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook/hcp-events")
@RequiredArgsConstructor
public class HcpWebhookController {

    @Autowired
    private LabelboxProjectService labelboxService;

    @PostMapping
    public ResponseEntity<String> handleHcpEvent(@RequestBody HcpEventPayload payload) {
//        try {
//            // Do the create event operation
//            if ("Create".equalsIgnoreCase(payload.getEventType())) {
//                labelboxService.handleCreateEvent(payload);
//            }
//            // Do the update event operation
//            else if ("Update".equalsIgnoreCase(payload.getEventType())) {
//                labelboxService.handleUpdateEvent(payload);
//            } else {
//                return ResponseEntity.badRequest().body("Unsupported event type");
//            }
//            return ResponseEntity.ok("Event processed successfully");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed: " + e.getMessage());
//        }

        return null;
    }
}
