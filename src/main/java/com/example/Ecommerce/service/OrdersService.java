package com.example.Ecommerce.service;

import java.util.List;import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.entity.Orders;
import com.example.Ecommerce.exception.ResourceNotFoundException;
import com.example.Ecommerce.repository.OrdersRepository;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository repo;

    @CacheEvict(value = "OrdersService", allEntries = true)

    public Orders save(Orders orders) {
        return repo.save(orders);
    }
    
    
    
    
    
    @Cacheable(value ="OrdersService")

    public List<Orders> getAll() {
        return repo.findAll();
    }
    
    
    
    
    
    @Cacheable(value ="OrdersService",key ="#id")

    public Orders getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order Not Found : " + id));
    }
    
    
    
    
    @CacheEvict(value = "OrdersService", key = "#id")

    public String delete(Long id) {
        repo.deleteById(id);
        return "Order Deleted Successfully";
    }

}