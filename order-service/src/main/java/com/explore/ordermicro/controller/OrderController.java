package com.explore.ordermicro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.explore.ordermicro.dto.OrderRequest;
import com.explore.ordermicro.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@PostMapping("/placeOrder")
	@ResponseStatus(code = HttpStatus.CREATED)
	public String placeOrder(@RequestBody OrderRequest orderRequest) {
		
		orderService.placeOrder(orderRequest);
		return "order placed successfully";
	}
}
