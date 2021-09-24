package com.dk.springmailapi.dto;

import lombok.Data;

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
}
