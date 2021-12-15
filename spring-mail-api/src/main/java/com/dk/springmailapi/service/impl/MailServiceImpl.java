package com.dk.springmailapi.service.impl;

import com.dk.springmailapi.dto.MailDTO;
import com.dk.springmailapi.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author Contus
 * @version 1.0
 * @since 09/24/2021
 */
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    /**
     * @param mailDTO
     * @return the response
     */
    @Override
    public String sendMail(MailDTO mailDTO) {
        try {
            String sql = "select * from employee where id=2";
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(mailDTO.getTo());
            simpleMailMessage.setText(mailDTO.getMessage());
            simpleMailMessage.setSubject(mailDTO.getSubject());
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed";
        }
        return "Success";
    }

    /**
     * @param multipartFile
     * @param to
     * @return
     */
    @Override
    public String sendMailWithAttachment(MultipartFile multipartFile, String to) {
        File sourceFile = null;
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            // true = multipart message
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(to);
            File file = new File(getClass().getClassLoader().getResource("application.properties").getPath());
            String parentPath = file.getParent();
            String fileName = multipartFile.getOriginalFilename();
            mimeMessageHelper.setSubject(fileName.split("\\.")[0]);
            mimeMessageHelper.setText("Hi, <br/> PFA", true);
            String pathName = parentPath + "\\" + fileName;
            multipartFile.transferTo(new File(pathName));
            sourceFile = new File(pathName);
            mimeMessageHelper.addAttachment(sourceFile.getName(), sourceFile);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed";
        }
        return "Success";
    }

    /**
     * @param mailDTO
     * @return the response as String
     * <p>
     * This method is used to send the Email
     * </p>
     */
    @Override
    public String sendEmailWithThymeleafTemplate(MailDTO mailDTO, Map<String, Object> templateModel) {
        Context thymeLeafContext = new Context();
        thymeLeafContext.setVariables(templateModel);
        String htmlBody = templateEngine.process("welcome.html", thymeLeafContext);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(mailDTO.getTo());
            mimeMessageHelper.setSubject(mailDTO.getSubject());
            mimeMessageHelper.setText(htmlBody, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Failed";
        }
        return "Success";
    }

    /**
     * @param to
     * @return
     */
    @Override
    public String sendMailAttachment(byte[] fileContent, String to) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            // true = multipart message
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("Test Mail");
            mimeMessageHelper.setText("Hi, <br/> PFA", true);
            final InputStream inputStream = new ByteArrayInputStream(fileContent);
            final DataSource attachment = new ByteArrayDataSource(inputStream, "application/octet-stream");
            mimeMessageHelper.addAttachment(getFileName(),attachment);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed";
        }
        return "Success";
    }

    public String getFileName() {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        return "users_"+currentDateTime+".xlsx";
    }
}

