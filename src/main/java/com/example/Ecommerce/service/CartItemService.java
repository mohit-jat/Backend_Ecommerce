package com.example.Ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.entity.CartItem;
import com.example.Ecommerce.entity.Cart;

import com.example.Ecommerce.entity.Product;
import com.example.Ecommerce.exception.ResourceNotFoundException;
import com.example.Ecommerce.repository.CartItemRepository;
import com.example.Ecommerce.repository.CartRepository;
import com.example.Ecommerce.repository.ProductRepository;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository repo;
    
    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private ProductRepository productRepo;

    
    
    public Page<CartItem> getAll(int page, int size) {
		Pageable pagable = PageRequest.of(page, size);
		return repo.findAll(pagable);

	}

    public CartItem save(CartItem cartItem) {

        if (cartItem.getDeleted() == null) {
            cartItem.setDeleted(false);
        }

        Cart cart = cartRepo.findById(cartItem.getCart().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart Not Found"));

        Product product = productRepo.findById(cartItem.getProduct().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));

        cartItem.setCart(cart);
        cartItem.setProduct(product);

        return repo.save(cartItem);
    }

    
    
    
    
      @Cacheable(value = "CartltemService")

    public List<CartItem> getAll() {
        return repo.findAll();
    }
      
      
      
      
    @CacheEvict(value = "CartltemService", key = "#id")

    public CartItem getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart Item Not Found : " + id));
    }
    
    
    
    public String delete(Long id) {
        repo.deleteById(id);
        return "Cart Item Deleted Successfully";
    }

}