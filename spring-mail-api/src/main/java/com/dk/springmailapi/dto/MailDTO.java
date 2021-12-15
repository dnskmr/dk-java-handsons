package com.dk.springmailapi.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Dinesh
 * @version 1.0
 * @since 09/24/2021
 */
@Data
public class MailDTO {
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String message;
    private byte[] fileContent;
}
