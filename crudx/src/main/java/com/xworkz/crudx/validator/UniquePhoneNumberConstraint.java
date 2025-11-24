package com.xworkz.crudx.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PhoneNumberValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
public @interface UniquePhoneNumberConstraint {
    String message() default "Duplicate PhoneNumber";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
