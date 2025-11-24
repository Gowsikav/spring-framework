package com.xworkz.crudx.service;

import com.xworkz.crudx.dto.CustomerDTO;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Boolean saveCustomerInfo(CustomerDTO customerDTO);
    Optional<CustomerDTO> getCustomerById(Integer id);
    Boolean updateCustomer(Integer id,  CustomerDTO customerDTO);
    Boolean deleteCustomer(Integer id);
    Boolean saveCustomersBatch( List<CustomerDTO> customerDTOs);
    Boolean updateCustomersBatch(List<CustomerDTO> customerDTOs);
    Boolean deleteCustomersBatch(List<Integer> ids);
    List<CustomerDTO> getAllCustomers();

}
