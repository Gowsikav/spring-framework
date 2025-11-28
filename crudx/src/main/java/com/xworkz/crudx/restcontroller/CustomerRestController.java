package com.xworkz.crudx.restcontroller;

import com.xworkz.crudx.dto.CustomerDTO;
import com.xworkz.crudx.service.CustomerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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


        if (customerService.deleteCustomer(id))
            return ResponseEntity.ok("Customer deleted successfully");
        else
            return ResponseEntity.status(404).body("Customer not found");
    }

    @PostMapping("/customers")
    public ResponseEntity<?> saveMultipleCustomers(@RequestBody List<@Valid CustomerDTO> customerDTOs) {

        if(customerService.saveCustomersBatch(customerDTOs))
        return ResponseEntity.ok("Batch customers saved successfully");
        else return ResponseEntity.status(500).body("unable to save customers");
    }

    @PutMapping("/customers")
    public ResponseEntity<String> updateMultipleCustomers(
            @Valid @RequestBody List<CustomerDTO> customerDTOs,
            BindingResult bindingResult) {

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

        if (customerService.deleteCustomersBatch(ids))
            return ResponseEntity.ok("Batch customers deleted successfully");
        else
            return ResponseEntity.status(404).body("Some customers not found");
    }

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {

        List<CustomerDTO> customers = customerService.getAllCustomers();
        if (customers.isEmpty()) {
            return ResponseEntity.status(404).body(customers);
        }
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/customers-page")
    public ResponseEntity<?> getDetailsForPagination(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {

        Page<CustomerDTO> customerPage = customerService.getAllCustomersByPagination(page - 1, size);

        if (customerPage.isEmpty()) {
            return ResponseEntity.badRequest().body("Unable to fetch");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("customers", customerPage.getContent());
        response.put("currentPage", customerPage.getNumber() + 1);
        response.put("totalItems", customerPage.getTotalElements());
        response.put("totalPages", customerPage.getTotalPages());

        return ResponseEntity.ok(response);
    }


}
