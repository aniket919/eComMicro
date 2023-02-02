package com.explore.ordermicro.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.explore.ordermicro.dto.OrderLineItemsDto;
import com.explore.ordermicro.dto.OrderRequest;
import com.explore.ordermicro.model.Order;
import com.explore.ordermicro.model.OrderLineItems;
import com.explore.ordermicro.repository.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;

	public void placeOrder(OrderRequest orderRequest) {
		
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		
		List<OrderLineItems> listOrderLineItems = orderRequest.getOrderLineItemsDtoList()
																.stream()
																.map(orderLineItemsDto -> mapToDto(orderLineItemsDto))
													//			.map(this::mapToDto)
																.toList();
		
		order.setOrderLineItemsList(listOrderLineItems);
		
		orderRepository.save(order);
	}

	private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
		
		OrderLineItems orderLineItems = new OrderLineItems();
		orderLineItems.setPrice(orderLineItemsDto.getPrice());
		orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
		orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
		
		return orderLineItems;
	}
	
}
