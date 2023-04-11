package com.explore.ordermicro.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.explore.ordermicro.dto.OrderRequest;
import com.explore.ordermicro.service.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@PostMapping("/placeOrder")
	@ResponseStatus(code = HttpStatus.CREATED)
	@CircuitBreaker(name="inventory", fallbackMethod = "placeOrderFallBack")
	@TimeLimiter(name="inventory")
	@Retry(name="inventory")
	// name="inventory" is coming from resilience4j "instances.inventory" attribute.
	public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest) {
		
	return CompletableFuture.supplyAsync(()-> orderService.placeOrder(orderRequest))	;
		
	}
	
	public CompletableFuture<String> placeOrderFallBack(OrderRequest orderRequest, RuntimeException exception) {
		
		// this logic will get executed in different thread
		return CompletableFuture.supplyAsync(()-> "Please try palcing order after some time !"); 
	}
}
