package com.dk.springmailapi.service;

import com.dk.springmailapi.dto.MailDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author Dinesh
 * @version 1.0
 * @since 09/24/2021
 */
public interface MailService {
    /**
     * @param mailDTO
     * @return response as String
     */
    String sendMail(MailDTO mailDTO);

    /**
     * @param multipartFile
     * @param to
     * @return
     */
    String sendMailWithAttachment(MultipartFile multipartFile, String to);

    /**
     *
     * @param mailDTO
     * @return the response as String
     */
    String sendEmailWithThymeleafTemplate(MailDTO mailDTO, Map<String, Object> templateModel);

    /**
     *
     * @param to
     * @return the response as String
     */
    String sendMailAttachment(byte[] fileContent, String to);
}
