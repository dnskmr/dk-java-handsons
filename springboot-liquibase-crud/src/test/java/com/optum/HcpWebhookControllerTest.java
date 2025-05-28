package com.optum;

import com.optum.controller.HcpWebhookController;
import com.optum.dto.HcpEventPayload;
import com.optum.service.LabelboxProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class HcpWebhookControllerTest {

    @Mock
    private LabelboxProjectService labelboxService;

    @InjectMocks
    private HcpWebhookController hcpWebhookController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHandleCreateEvent_Success() {
        // Arrange
        HcpEventPayload payload = new HcpEventPayload();
        payload.setEventType("Create");

        doNothing().when(labelboxService).handleCreateEvent(any(HcpEventPayload.class));

        // Act
        ResponseEntity<String> response = hcpWebhookController.handleHcpEvent(payload);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Event processed successfully", response.getBody());
        verify(labelboxService, times(1)).handleCreateEvent(payload);
        verify(labelboxService, never()).handleUpdateEvent(any());
    }

    @Test
    public void testHandleUpdateEvent_Success() {
        // Arrange
        HcpEventPayload payload = new HcpEventPayload();
        payload.setEventType("Update");

        doNothing().when(labelboxService).handleUpdateEvent(any(HcpEventPayload.class));

        // Act
        ResponseEntity<String> response = hcpWebhookController.handleHcpEvent(payload);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Event processed successfully", response.getBody());
        verify(labelboxService, times(1)).handleUpdateEvent(payload);
        verify(labelboxService, never()).handleCreateEvent(any());
    }

    @Test
    public void testHandleUnsupportedEventType_ReturnsBadRequest() {
        // Arrange
        HcpEventPayload payload = new HcpEventPayload();
        payload.setEventType("Delete");

        // Act
        ResponseEntity<String> response = hcpWebhookController.handleHcpEvent(payload);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Unsupported event type", response.getBody());
        verifyNoInteractions(labelboxService);
    }

    @Test
    public void testHandleCreateEvent_ThrowsException_ReturnsServerError() {
        // Arrange
        HcpEventPayload payload = new HcpEventPayload();
        payload.setEventType("Create");

        doThrow(new RuntimeException("Create failed")).when(labelboxService).handleCreateEvent(any(HcpEventPayload.class));

        // Act
        ResponseEntity<String> response = hcpWebhookController.handleHcpEvent(payload);

        // Assert
        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Failed: Create failed"));
        verify(labelboxService, times(1)).handleCreateEvent(payload);
    }

    @Test
    public void testHandleUpdateEvent_ThrowsException_ReturnsServerError() {
        // Arrange
        HcpEventPayload payload = new HcpEventPayload();
        payload.setEventType("Update");

        doThrow(new RuntimeException("Update failed")).when(labelboxService).handleUpdateEvent(any(HcpEventPayload.class));

        // Act
        ResponseEntity<String> response = hcpWebhookController.handleHcpEvent(payload);

        // Assert
        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Failed: Update failed"));
        verify(labelboxService, times(1)).handleUpdateEvent(payload);
    }
}

