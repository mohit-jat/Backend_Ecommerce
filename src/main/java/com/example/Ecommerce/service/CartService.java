package com.example.Ecommerce.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.entity.Cart;
import com.example.Ecommerce.entity.Customer;
import com.example.Ecommerce.exception.ResourceNotFoundException;
import com.example.Ecommerce.repository.CartRepository;
import com.example.Ecommerce.repository.CustomerRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository repo;
    
    
    public Page<Cart> getAll(int page, int size) {
		Pageable pagable = PageRequest.of(page, size);
		return repo.findAll(pagable);
	}
   
    @Autowired
    private CustomerRepository customerRepo;

    public Cart save(Cart cart) {

        if (cart.getDeleted() == null) {
            cart.setDeleted(false);
        }

        Long customerId = cart.getCustomer().getId();
        

        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer Not Found"));

        cart.setCustomer(customer);
        System.out.println("Received Cart = " + cart);

        return repo.save(cart);
    
    }   
    
    @Cacheable(value = "CartService")

    public List<Cart> getAll() {
        return repo.findAll();
    }
    
    
    
    @Cacheable(value = "CartService", key = "#id")

    public Cart getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart Not Found : " + id));
    }
    
    
    
    @CacheEvict(value = "CartService", key = "#id")

    public String delete(Long id) {
        repo.deleteById(id);
        return "Cart Deleted Successfully";
    }

}