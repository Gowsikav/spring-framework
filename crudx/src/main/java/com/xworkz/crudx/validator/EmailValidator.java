package com.xworkz.crudx.validator;

import com.xworkz.crudx.repository.CustomerRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailValidator implements ConstraintValidator<UniqueEmailConstraint,String> {

    @Autowired
    private CustomerRepository customerRepository;

    public EmailValidator()
    {
        log.info("EmailValidator constructor");
    }

    @Override
    public void initialize(UniqueEmailConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email == null || email.isEmpty()) {
            return true;
        }
        return customerRepository.findByEmail(email) == null;
    }
}
