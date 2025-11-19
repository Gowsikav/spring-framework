package com.xworkz.crudx.repository;

import com.xworkz.crudx.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity,Integer> {

}
