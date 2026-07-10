package com.example.Ecommerce.controller;

import java.util.List;
import org.springframework.data.domain.Page;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.Ecommerce.entity.CartItem;
import com.example.Ecommerce.service.CartItemService;

@RestController
@RequestMapping("/cartitem")
public class CartItemController {

    @Autowired
    private CartItemService service;

    @PostMapping
    public CartItem save(@RequestBody CartItem cartItem) {
        return service.save(cartItem);
    }

    @GetMapping
    public List<CartItem> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public CartItem getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return service.delete(id);
    }
    @GetMapping("/cartitem/{page}")
	public Page<CartItem> findPage(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return service.getAll(page, size);
	}
}