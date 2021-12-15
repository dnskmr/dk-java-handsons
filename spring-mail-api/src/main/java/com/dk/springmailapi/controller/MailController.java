package com.dk.springmailapi.controller;

import com.dk.springmailapi.dto.MailDTO;
import com.dk.springmailapi.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dinesh
 * @version 1.0
 * @since 09/24/2021
 */
@RestController
public class MailController {

    @Autowired
    private MailService mailService;

    /**
     * @param mailDTO
     * @return the response
     */
    @PostMapping("/mail/send")
    public ResponseEntity sendMail(@RequestBody MailDTO mailDTO) {
        String response = mailService.sendMail(mailDTO);
        if ("Success".equals(response)) {
            return new ResponseEntity<>("Successfully Sent", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to Send", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param multipartFile
     * @param to
     * @return the response
     */
    @PostMapping("/mail/send/attachment")
    public ResponseEntity sendMailWithAttachment(@RequestParam("file") MultipartFile multipartFile, @RequestParam("to") String to) {
        String response = mailService.sendMailWithAttachment(multipartFile, to);
        if ("Success".equals(response)) {
            return new ResponseEntity<>("Successfully Sent", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to Send", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * @param mailDTO
     * @return the response
     */
    @PostMapping("/mail/send/template")
    public ResponseEntity sendEmailWithThymeleafTemplate(@RequestBody MailDTO mailDTO) {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("name", "Dinesh");
        String response = mailService.sendEmailWithThymeleafTemplate(mailDTO,templateModel);
        if ("Success".equals(response)) {
            return new ResponseEntity<>("Successfully Sent", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to Send", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @return the response
     */
    @PostMapping("/mail/sendattachment")
    public ResponseEntity sendMailAttachment(@RequestBody MailDTO mailDTO) {
        String response = mailService.sendMailAttachment(mailDTO.getFileContent(),mailDTO.getTo());
        if ("Success".equals(response)) {
            return new ResponseEntity<>("Successfully Sent", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to Send", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
