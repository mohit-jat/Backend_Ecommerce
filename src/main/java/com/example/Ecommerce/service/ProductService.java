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
import com.example.Ecommerce.exception.ResourceNotFoundException;
import com.example.Ecommerce.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;
	public Page<Product> getAll(int page, int size) {
		Pageable pagable = PageRequest.of(page, size);
		return repo.findAll(pagable);

	}
	
	
	
	
	
	@CacheEvict(value = "ProductService", allEntries = true)

    public Product save(Product product) {
        return repo.save(product);
    }
	
	
	
	
    @Cacheable(value ="ProductService")

    public List<Product> getAll() {
        return repo.findAll();
    }
    
    
    
    
    
    @Cacheable(value ="ProductService",key ="#id")

    public Product getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found : " + id));
    }
    
    
    
    
    
    
    @CacheEvict(value = "ProductService", allEntries = true)

    public Product update(Long id, Product product) {

        return repo.findById(id).map(p -> {

            p.setName(product.getName());
            p.setDescription(product.getDescription());
            p.setPrice(product.getPrice());
            p.setStock(product.getStock());
            p.setVendor(product.getVendor());
            p.setCategory(product.getCategory());

            return repo.save(p);

        }).orElseThrow(() -> new ResourceNotFoundException("Product Not Found : " + id));

    }
    
    
    
    
    
    @CacheEvict(value = "ProductService", key = "#id")

    public String delete(Long id) {
        repo.deleteById(id);
        return "Product Deleted Successfully";
    }
    
    
    
    
    

    public List<Product> sorting(String field) {
        return repo.findAll(Sort.by(field));
    }

    
    
    
    
    public List<Product> search(String name){

    	return repo.findByNameContainingIgnoreCase(name);

    }
 
}