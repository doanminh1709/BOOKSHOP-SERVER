package project.spring.quanlysach.application.validation;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class EmailValidation implements Predicate<String> {
    //Interface Predicate is one of interface generic in Java provide 'test()' method
    //to check a certain condition return true or false

    @Override
    public boolean test(String s) {
        return true;
    }
}
