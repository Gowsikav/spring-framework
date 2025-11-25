package com.xworkz.crudx.service;

import com.xworkz.crudx.dto.CustomerDTO;
import com.xworkz.crudx.entity.CustomerEntity;
import com.xworkz.crudx.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerServiceImpl() {
        log.info("CustomerServiceImpl constructor");
    }

    @Override
    public Boolean saveCustomerInfo(CustomerDTO customerDTO) {
        log.info("saveCustomerInfo method in CustomerService");
        CustomerEntity customerEntity = new CustomerEntity();
        BeanUtils.copyProperties(customerDTO, customerEntity);
        try {
            customerEntity = customerRepository.save(customerEntity);
            if (customerEntity != null) {
                log.info("data saved");
                return true;
            } else {
                log.warn("Data not saved");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(Integer id) {
        log.info("getCustomerById method in customerService");
        Optional<CustomerEntity> customerEntity = customerRepository.findById(id);
        if (customerEntity.isPresent()) {
            CustomerDTO customerDTO = new CustomerDTO();
            BeanUtils.copyProperties(customerEntity.get(), customerDTO);
            return Optional.of(customerDTO);
        }
        return Optional.empty();
    }

    public Boolean updateCustomer(Integer id, CustomerDTO dto) {
        log.info("updateCustomer method in customerService");
        Optional<CustomerEntity> opt = customerRepository.findById(id);
        if (opt.isEmpty())
            return false;

        CustomerEntity entity = opt.get();

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setAddress(dto.getAddress());

        customerRepository.save(entity);
        return true;
    }

    public Boolean deleteCustomer(Integer id) {
        log.info("deleteCustomer method in customerService");
        if (!customerRepository.existsById(id)) {
            return false;
        }
        customerRepository.deleteById(id);
        return true;
    }

    @Transactional
    public Boolean saveCustomersBatch(List<CustomerDTO> customerDTOs) {
        log.info("saveCustomersBatch method in customerService");
        List<CustomerEntity> entities = customerDTOs.stream().map(dto -> {
            CustomerEntity entity = new CustomerEntity();
            BeanUtils.copyProperties(dto, entity);
            return entity;
        }).collect(Collectors.toList());

        customerRepository.saveAll(entities);
        return true;
    }

    @Transactional
    public Boolean updateCustomersBatch(List<CustomerDTO> dtos) {
        log.info("updateCustomersBatch method in customerService");

        for (CustomerDTO dto : dtos) {
            Optional<CustomerEntity> opt = customerRepository.findById(dto.getCustomerId());
            if (opt.isEmpty())
                return false;

            CustomerEntity e = opt.get();
            e.setFirstName(dto.getFirstName());
            e.setLastName(dto.getLastName());
            e.setEmail(dto.getEmail());
            e.setPhoneNumber(dto.getPhoneNumber());
            e.setAddress(dto.getAddress());
        }

        return true;
    }

    @Transactional
    public Boolean deleteCustomersBatch(List<Integer> ids) {
        log.info("deleteCustomersBatch method in customerService");

        for (Integer id : ids) {
            if (!customerRepository.existsById(id))
                return false;
        }
        customerRepository.deleteAllById(ids);

        return true;
    }

    public List<CustomerDTO> getAllCustomers() {
        log.info("getAllCustomers method in customerService");
        List<CustomerEntity> entities = customerRepository.findAll();

        return entities.stream().map(entity -> {
            CustomerDTO dto = new CustomerDTO();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public Page<CustomerDTO> getAllCustomersByPagination(Integer page, Integer size) {
        log.info("getAllCustomersByPagination method in customer service");
        Pageable pageable = PageRequest.of(page, size);
        Page<CustomerEntity> pageResult = customerRepository.findAll(pageable);
        return pageResult.map(this::convertToDTO);
    }
    private CustomerDTO convertToDTO(CustomerEntity entity) {
        CustomerDTO dto = new CustomerDTO();
        dto.setCustomerId(entity.getCustomerId());
        dto.setLastName(entity.getLastName());
        dto.setFirstName(entity.getFirstName());
        dto.setEmail(entity.getEmail());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setAddress(entity.getAddress());
        return dto;
    }

}
