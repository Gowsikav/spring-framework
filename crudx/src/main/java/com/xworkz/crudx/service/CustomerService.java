package com.xworkz.crudx.service;

import com.xworkz.crudx.dto.CustomerDTO;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Boolean saveCustomerInfo(CustomerDTO customerDTO);
    Optional<CustomerDTO> getCustomerById(Integer id);
    Boolean updateCustomer(Integer id, @Valid CustomerDTO customerDTO);
    Boolean deleteCustomer(Integer id);
    Boolean saveCustomersBatch(@Valid List<CustomerDTO> customerDTOs);
    Boolean updateCustomersBatch(@Valid List<CustomerDTO> customerDTOs);
    Boolean deleteCustomersBatch(List<Integer> ids);
    List<CustomerDTO> getAllCustomers();

}
