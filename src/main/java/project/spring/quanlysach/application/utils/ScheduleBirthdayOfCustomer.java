package project.spring.quanlysach.application.utils;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import project.spring.quanlysach.application.auth_service.MailService;
import project.spring.quanlysach.application.services.ICustomerService;
import project.spring.quanlysach.domain.entity.Customer;

import javax.mail.MessagingException;
import java.util.List;

@Component
public class ScheduleBirthdayOfCustomer {

    @Autowired
    ICustomerService customerService;
    @Autowired
    MailService mailService;
    @Autowired
    private SpringTemplateEngine templateEngine;

    private static final Logger logger = Logger.getLogger(ScheduleBirthdayOfCustomer.class);
    //<second><minute> <hour> <day-of-month> <month> <day-of-week><year> <command>
    @Scheduled(cron = "0 0 18-19 * * ?")
    public void sentGreetingsToCustomer() {

        List<Customer> customerList = customerService.searchCustomerBirthdayToday();
        if (customerList.size() > 0) {
            customerList.forEach(item -> {
                Context context = new Context();
                context.setVariable("name", item.getFullName());
                String html = templateEngine.process("birthday.html", context);
                try {
                    mailService.sendGreeting("Happy Birthday To You", html, item.getEmail(), item.getFullName(), true);
                } catch (MessagingException ignored) {
                    logger.error(ignored.getMessage());
                }
            });
        }
    }
}
