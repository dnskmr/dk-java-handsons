package com.dk.springmailapi.service;

import com.dk.springmailapi.dto.MailDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Dinesh
 * @version 1.0
 * @since 09/24/2021
 *
 */
public interface MailService {
    /**
     *
     * @param mailDTO
     * @return response as String
     */
    String sendMail(MailDTO mailDTO);

    /**
     *
     * @param multipartFile
     * @param to
     * @return
     */
    String sendMailWithAttachment(MultipartFile multipartFile, String to);
}
