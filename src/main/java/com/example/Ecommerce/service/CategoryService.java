package com.example.Ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.Ecommerce.exception.ResourceNotFoundException;
import com.example.Ecommerce.entity.Category;
import com.example.Ecommerce.entity.Product;
import com.example.Ecommerce.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repo;
    public Page<Category> getAll(int page, int size) {
		Pageable pagable = PageRequest.of(page, size);
		return repo.findAll(pagable);

	}

    
    
    @CacheEvict(value = "CategoryService", allEntries = true)
    public Category save(Category category) {
        return repo.save(category);
    }
    @Cacheable(value = "CategoryService")

    public List<Category> getAll() {
        return repo.findAll();
    }
    @Cacheable(value = "CategoryService", key = "#id")

    public Category getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category Not Found : " + id));
    }

    
    @CacheEvict(value = "CategoryService", allEntries = true)
    public Category update(Long id, Category category) {

        return repo.findById(id).map(c -> {

            c.setName(category.getName());
            c.setDescription(category.getDescription());

            return repo.save(c);

        }).orElseThrow(() -> new ResourceNotFoundException("Category Not Found : " + id));

    }
    
    @CacheEvict(value = "CategoryService", key = "#id")


    public String delete(Long id) {
        repo.deleteById(id);
        return "Category Deleted Successfully";
    }

    public List<Category> sorting(String field) {
        return repo.findAll(Sort.by(field));
    }

}