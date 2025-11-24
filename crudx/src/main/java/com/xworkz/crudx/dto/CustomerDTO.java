package com.xworkz.crudx.dto;

import com.xworkz.crudx.validator.UniqueEmailConstraint;
import com.xworkz.crudx.validator.UniquePhoneNumberConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDTO {

    private Integer customerId;

    @NotBlank
    @Size(min = 2, max = 30)
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 30)
    private String lastName;

    @NotBlank
    @Pattern(regexp = "^[6-9]\\d{9}$")
    @UniquePhoneNumberConstraint

    private String phoneNumber;

    @NotBlank
    @Email
    @UniqueEmailConstraint
    private String email;

    @NotBlank
    @Size(min = 5, max = 100)
    private String address;
}
