package com.example.Ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.entity.Product;
import com.example.Ecommerce.entity.Vendor;
import com.example.Ecommerce.exception.ResourceNotFoundException;
import com.example.Ecommerce.repository.VendorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class VendorService {

    @Autowired
    private VendorRepository repo;
    public Page<Vendor> getAll(int page, int size) {
		Pageable pagable = PageRequest.of(page, size);
		return repo.findAll(pagable);

	}

    @CacheEvict(value="VendorService", allEntries = true)

    public Vendor save(Vendor vendor) {
        return repo.save(vendor);
    }

    @Cacheable("VendorService")

    public List<Vendor> getAll() {
        return repo.findAll();
    }


    @CacheEvict(value="VendorService", allEntries = true)
public Vendor getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException ("Vendor Not Found : " + id));
    }

    
    
    
    @CacheEvict(value="VendorService", allEntries = true)

    public Vendor update(Long id, Vendor vendor) {

        return repo.findById(id).map(v -> {

            v.setName(vendor.getName());
            v.setCompanyName(vendor.getCompanyName());
            v.setEmail(vendor.getEmail());
            v.setPhone(vendor.getPhone());
            v.setAddress(vendor.getAddress());

            return repo.save(v);

        }).orElseThrow(() -> new ResourceNotFoundException("Vendor Not Found : " + id));

    }
    
    
    
    
    @CacheEvict(value="VendorService", allEntries = true)

    public String delete(Long id) {
	
        repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor Not Found : " + id));

        repo.deleteById(id);

        return "Vendor Deleted Successfully";
    }    
    
    
    

    public List<Vendor> sorting(String field) {

        return repo.findAll(Sort.by(field));

    
    }

}