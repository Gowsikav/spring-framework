package com.xworkz.crudx.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmailConstraint {
    String message() default "Duplicate Email";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
