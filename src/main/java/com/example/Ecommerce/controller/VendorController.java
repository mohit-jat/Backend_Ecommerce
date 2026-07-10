package com.example.Ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.example.Ecommerce.entity.Vendor;
import com.example.Ecommerce.service.VendorService;

@RestController
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    private VendorService service;

    @PostMapping
    public Vendor save(@RequestBody Vendor vendor) {
        return service.save(vendor);
    }

    @GetMapping
    public List<Vendor> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Vendor getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Vendor update(@PathVariable Long id, @RequestBody Vendor vendor) {
        return service.update(id, vendor);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @GetMapping("/sort/{field}")
    public List<Vendor> sorting(@PathVariable String field) {
        return service.sorting(field);
    }
 
    @GetMapping("/vendor/{page}")
	public Page<Vendor> findPage(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return service.getAll(page, size);
	}
   
}