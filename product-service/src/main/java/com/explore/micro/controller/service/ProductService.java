package com.explore.micro.controller.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.explore.micro.controller.dto.ProductDto;
import com.explore.micro.controller.dto.ProductResponse;
import com.explore.micro.model.Products;
import com.explore.micro.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	public Products createProduct(ProductDto productDto) {
		
		Products product = Products.builder()
							.name(productDto.getName())
							.description(productDto.getDescription())
							.price(productDto.getPrice())
							.build();
		
		
		return productRepository.save(product);
		
	}

	public List<ProductResponse> getAllProducts() {
		
		List<Products> products = productRepository.findAll();
		
		return products.stream().map(product -> mapToProductResponse(product)).toList();
	}

	private ProductResponse mapToProductResponse(Products product) {
		
		return ProductResponse.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice())
				.build();
	}

	
}
