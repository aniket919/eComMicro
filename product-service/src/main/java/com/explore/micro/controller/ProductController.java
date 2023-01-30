package com.explore.micro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.explore.micro.controller.dto.ProductDto;
import com.explore.micro.controller.dto.ProductResponse;
import com.explore.micro.controller.service.ProductService;
import com.explore.micro.model.Products;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/product")
@Slf4j
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	
	@PostMapping("/new")
	public ResponseEntity<Products> createProduct(@RequestBody ProductDto productDto){
		
		Products pr = productService.createProduct(productDto);
		
		log.info("Product is created : " + pr);
		
		return new ResponseEntity<Products>(pr, HttpStatus.CREATED);
	}

	@GetMapping("/getAllProducts")
	public ResponseEntity<List<ProductResponse>> getAllProducts(){
		
		return new ResponseEntity<List<ProductResponse>>(productService.getAllProducts(), HttpStatus.OK);
	}
}
