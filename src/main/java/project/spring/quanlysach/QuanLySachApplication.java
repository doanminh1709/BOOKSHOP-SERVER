package project.spring.quanlysach;

import com.github.slugify.Slugify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import project.spring.quanlysach.application.auth_service.CustomerDetailServiceImp;

@SpringBootApplication
public class QuanLySachApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuanLySachApplication.class, args);
    }

    @Bean
    public Slugify slugify() {
        return new Slugify();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setCacheSeconds(10); //reload messages every 10 seconds
        return messageSource;
    }

    @Autowired
    private CustomerDetailServiceImp customerDetailServiceImp;

//    @Bean
//    public AuthTokenFilter authTokenFilter() {
//        return new AuthTokenFilter();
//    }
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(customerDetailServiceImp);
//        provider.setPasswordEncoder(passwordEncoder());
//        return provider;
//    }
}
