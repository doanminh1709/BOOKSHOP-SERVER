package project.spring.quanlysach.adapter.web.base;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)//được giữ lại trong khi ứng dụng đang chạy
@Documented// cho biết anotation nên đk đưa vào ứng dụng chạy tự động
@RestController
@RequestMapping("/api/v0")
public @interface RestApiV0 {
}
