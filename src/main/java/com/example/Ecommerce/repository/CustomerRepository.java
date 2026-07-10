package com.example.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Ecommerce.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Page<Customer> findAll(Pageable pageable);
}