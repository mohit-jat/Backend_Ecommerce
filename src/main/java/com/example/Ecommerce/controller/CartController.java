package com.example.Ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.Ecommerce.entity.Cart;
import com.example.Ecommerce.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService service;

    @PostMapping
    public Cart save(@RequestBody Cart cart) {
    	
    	System.out.println("Customer = " + cart.getCustomer());
    	System.out.println("Customer Id = " +
    	    (cart.getCustomer() != null ? cart.getCustomer().getId() : null));
        return service.save(cart);
    }

    @GetMapping
    public List<Cart> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Cart getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return service.delete(id);
    }
}