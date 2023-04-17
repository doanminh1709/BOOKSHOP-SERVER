package project.spring.quanlysach.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    Cloudinary configCloudinary(){
        Map<String , String> config = new HashMap<>();
        config.put("api_key" , "128276567885464");
        config.put("api_secret" , "QUaDfa8uB-IIkG9Rd7D1JaUOu4M");
        config.put("cloud_name" , "dongkheclub");
        return new Cloudinary(config);
    }
}
