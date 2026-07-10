package com.example.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Ecommerce.entity.Vendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface VendorRepository extends JpaRepository<Vendor, Long> {
Page<Vendor> findAll(Pageable pageable);
}