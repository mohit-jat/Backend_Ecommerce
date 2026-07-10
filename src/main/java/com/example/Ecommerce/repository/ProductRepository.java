package com.example.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Ecommerce.entity.Product;
import com.example.Ecommerce.entity.Vendor;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface ProductRepository extends JpaRepository<Product, Long> {
	Page<Product> findAll(Pageable pageable);
	List<Product> findByNameContainingIgnoreCase(String name);


}