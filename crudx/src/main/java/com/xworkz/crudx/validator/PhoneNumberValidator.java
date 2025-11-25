package com.xworkz.crudx.validator;

import com.xworkz.crudx.repository.CustomerRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PhoneNumberValidator implements ConstraintValidator<UniquePhoneNumberConstraint,String> {

    @Autowired
    private CustomerRepository customerRepository;

    public PhoneNumberValidator()
    {
        log.info("PhoneNumberValidator constructor");
    }

    @Override
    public void initialize(UniquePhoneNumberConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        if(phoneNumber==null || phoneNumber.isEmpty())
        {
            return true;
        }
        return customerRepository.findByPhoneNumber(phoneNumber)==null;
    }
}
