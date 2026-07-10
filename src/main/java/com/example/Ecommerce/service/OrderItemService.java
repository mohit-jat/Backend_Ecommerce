package com.example.Ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.entity.OrderItem;
import com.example.Ecommerce.entity.Product;
import com.example.Ecommerce.exception.ResourceNotFoundException;
import com.example.Ecommerce.repository.OrderItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository repo;
    public Page<OrderItem> getAll(int page, int size) {
		Pageable pagable = PageRequest.of(page, size);
		return repo.findAll(pagable);

	}

@CacheEvict(value = "OrderItemService", allEntries = true)

public OrderItem save(OrderItem orderItem) {
        return repo.save(orderItem);
    }



    @Cacheable(value ="OrderItemService")

    public List<OrderItem> getAll() {
        return repo.findAll();
    }
    
    
    
    @Cacheable(value ="OrderItemService",key ="#id")

    public OrderItem getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order Item Not Found : " + id));
    }
    
    
    
    @CacheEvict(value ="OrderItemService",key ="#id")

    public String delete(Long id) {
        repo.deleteById(id);
        return "Order Item Deleted Successfully";
    }

}