package com.explore.micro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.explore.micro.model.Products;
import com.explore.micro.repository.ProductRepository;

@RestController
@RequestMapping("/api/product")
public class ProductController {
	
	@Autowired
	private ProductRepository repo;
	
	
	@PostMapping("/new")
	public ResponseEntity<Products> createProduct(@RequestBody Products product){
		
		Products pr = repo.save(product);
		return new ResponseEntity<Products>(pr, HttpStatus.CREATED);
	}

}
