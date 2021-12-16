package com.dk.springexcelexport.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Dinesh
 * @version 1.0
 * @since 09/24/2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailDTO {
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String message;
    private byte[] fileContent;
}
