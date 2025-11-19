package com.xworkz.crudx.restcontroller;

import com.xworkz.crudx.dto.CustomerDTO;
import com.xworkz.crudx.service.CustomerService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;

    public CustomerRestController() {
        log.info("CustomerRestController constructor");
    }

    @PostMapping("/customer")
    public ResponseEntity<String> saveSingleCustomer(@Valid CustomerDTO customerDTO, BindingResult bindingResult) {
        log.info("saveSingleCustomer method in customerRestController");
        if (bindingResult.hasErrors()) {
            log.error("fields error");
            String errors = bindingResult.getFieldErrors().stream()
                    .map(e -> e.getField() + " -> " + e.getDefaultMessage())
                    .collect(Collectors.joining("\n"));
            log.error("Errors: {}" , errors);
            return ResponseEntity.internalServerError().body(errors);
        }
        if (customerService.saveCustomerInfo(customerDTO))
            return ResponseEntity.ok("Data saved successfully");
        else return ResponseEntity.status(500).body("unable to save");
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<String> getCustomer(@PathVariable Integer id) {
        log.info("getCustomer method in restController");

        Optional<CustomerDTO> customer = customerService.getCustomerById(id);
        if (customer.isEmpty()) {
            return ResponseEntity.status(404).body("Customer not found");
        } else {
            return ResponseEntity.ok(customer.get().toString());
        }
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<String> updateCustomer(
            @PathVariable Integer id,
            @Valid CustomerDTO customerDTO,
            BindingResult bindingResult)
    {
        log.info("updateCustomer method in RestController");

        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream()
                    .map(e -> e.getField() + " -> " + e.getDefaultMessage())
                    .collect(Collectors.joining("\n"));

            log.error("Validation errors: {}",errors);
            return ResponseEntity.badRequest().body(errors);
        }

        if (customerService.updateCustomer(id, customerDTO))
            return ResponseEntity.ok("Customer updated successfully");
        else
            return ResponseEntity.status(404).body("Customer not found");
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Integer id) {

        log.info("deleteCustomer method in RestController");

        if (customerService.deleteCustomer(id))
            return ResponseEntity.ok("Customer deleted successfully");
        else
            return ResponseEntity.status(404).body("Customer not found");
    }

    @PostMapping("/customers")
    public ResponseEntity<?> saveMultipleCustomers(@RequestBody List<CustomerDTO> customerDTOs) {

        log.info("saveMultipleCustomers method in restController");

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        List<String> errors = new ArrayList<>();
        int index = 0;
        for (CustomerDTO dto : customerDTOs) {
            Set<ConstraintViolation<CustomerDTO>> violations = validator.validate(dto);

            for (ConstraintViolation<CustomerDTO> v : violations) {
                errors.add("Customer[" + index + "] -> " +
                        v.getPropertyPath() + " : " + v.getMessage());
            }
            index++;
        }
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        if(customerService.saveCustomersBatch(customerDTOs))
        return ResponseEntity.ok("Batch customers saved successfully");
        else return ResponseEntity.status(500).body("unable to save customers");
    }

    @PutMapping("/customers")
    public ResponseEntity<String> updateMultipleCustomers(
            @Valid @RequestBody List<CustomerDTO> customerDTOs,
            BindingResult bindingResult) {
        log.info("updateMultipleCustomers method in restController");
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream()
                    .map(e -> e.getField() + " -> " + e.getDefaultMessage())
                    .collect(Collectors.joining("\n"));
            return ResponseEntity.badRequest().body(errors);
        }

        if (customerService.updateCustomersBatch(customerDTOs))
            return ResponseEntity.ok("Batch customers updated successfully");
        else
            return ResponseEntity.status(404).body("Some customers not found");
    }

    @DeleteMapping("/customers")
    public ResponseEntity<String> deleteMultipleCustomers(@RequestBody List<Integer> ids) {
        log.info("deleteMultipleCustomers method in customerRestController");

        if (customerService.deleteCustomersBatch(ids))
            return ResponseEntity.ok("Batch customers deleted successfully");
        else
            return ResponseEntity.status(404).body("Some customers not found");
    }

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {

        log.info("getAllCustomers method in rest controller");

        List<CustomerDTO> customers = customerService.getAllCustomers();
        if (customers.isEmpty()) {
            return ResponseEntity.status(404).body(customers);
        }
        return ResponseEntity.ok(customers);
    }

}
