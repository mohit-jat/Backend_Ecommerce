package com.example.Ecommerce.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.Ecommerce.exception.ResourceNotFoundException;
import com.example.Ecommerce.entity.Customer;
import com.example.Ecommerce.entity.Product;
import com.example.Ecommerce.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repo;
    public Page<Customer> getAll(int page, int size) {
		Pageable pagable = PageRequest.of(page, size);
		return repo.findAll(pagable);

	}

    @CacheEvict(value = "CustomerService", allEntries = true)

    public Customer save(Customer customer) {
        return repo.save(customer);
    }
    @Cacheable(value = "CustomerService")

    public List<Customer> getAll() {
        return repo.findAll();
    }
    
    @Cacheable(value = "CustomerService", key = "#id")


    public Customer getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer Not Found : " + id));
    }

    
    @CacheEvict(value = "CustomerService", allEntries=true)

    public Customer update(Long id, Customer customer) {

        return repo.findById(id).map(c -> {

            c.setName(customer.getName());
            c.setEmail(customer.getEmail());
            c.setPassword(customer.getPassword());
            c.setPhone(customer.getPhone());
            c.setAddress(customer.getAddress());

            return repo.save(c);

        }).orElseThrow(() -> new ResourceNotFoundException("Customer Not Found : " + id));

    }
    @CacheEvict(value = "CustomerService", key = "#id")

    public String delete(Long id) {
        repo.deleteById(id);
        return "Customer Deleted Successfully";
    }

    public List<Customer> sorting(String field) {
        return repo.findAll(Sort.by(field));
  
    }

}