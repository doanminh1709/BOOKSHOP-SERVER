package project.spring.quanlysach.application.email;

import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

public interface EmailSender {
    void sent(String to, String email) throws MessagingException;
}
