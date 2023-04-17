package project.spring.quanlysach.application.auth_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import project.spring.quanlysach.application.email.EmailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class MailService implements EmailSender {

    private final Logger log = LoggerFactory.getLogger(MailService.class);
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sent(String to, String email) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());
            messageHelper.setSubject("Please confirmation your email");
            messageHelper.setTo(to);
            messageHelper.setText(email, true);
            messageHelper.setFrom("doanducminh11082002@gmail.com");
            javaMailSender.send(message);
        } catch (MessagingException ex) {
            log.error("Progress send mail has error : " + ex);
        }
    }
    //help message MIME type , usually MimeMessageHelper to create MimeMessage

    @Async
    public void sendNewPassword(String to, String content) throws MessagingException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());
            messageHelper.setSubject("Your new password ");
            messageHelper.setTo(to);
            messageHelper.setText(content);
            messageHelper.setFrom("doanducminh11082002@gmail.com");
            javaMailSender.send(message);
        } catch (MessagingException exception) {
            log.error("Send mail failed : " + exception);
        }
    }
}