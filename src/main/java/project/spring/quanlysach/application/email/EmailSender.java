package project.spring.quanlysach.application.email;


import javax.mail.MessagingException;

public interface EmailSender {
    void sent(String to, String email) throws MessagingException;

    void sendGreeting(String subject , String content , String email , String name , Boolean isHtmlFormat) throws MessagingException;
}
