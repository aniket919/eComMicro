package com.explore.ordermicro.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import com.explore.ordermicro.dto.InventoryResponse;
import com.explore.ordermicro.dto.OrderLineItemsDto;
import com.explore.ordermicro.dto.OrderRequest;
import com.explore.ordermicro.model.Order;
import com.explore.ordermicro.model.OrderLineItems;
import com.explore.ordermicro.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private WebClient webClient;			// webClient default mode of communication : Async
	
	@Autowired
	private RestTemplate restTemplate;

	public void placeOrder(OrderRequest orderRequest) {
		
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		
		List<OrderLineItems> listOrderLineItems = orderRequest.getOrderLineItemsDtoList()
																.stream()
																.map(orderLineItemsDto -> mapToDto(orderLineItemsDto))
													//			.map(this::mapToDto)
																.toList();
		
		order.setOrderLineItemsList(listOrderLineItems);
		
		List<String> skuCodesList = order.getOrderLineItemsList().stream()
									//	.map(orderLineItem -> orderLineItem.getSkuCode())
										.map(OrderLineItems::getSkuCode)					// using method reference.
										.toList();
		
		System.out.println("skuCodesList -> " + skuCodesList);
		
		Map<String, Integer> itemAndQuantity = order.getOrderLineItemsList().stream()
											.collect(Collectors.toMap(OrderLineItems::getSkuCode, OrderLineItems::getQuantity));
		
		// call inventory service, check if product is in stock, if yes then place order.
		
		InventoryResponse[] inventoryResponseArray = webClient.get()
					.uri("http://localhost:8082/api/inventory/checkStock", UriBuilder -> UriBuilder.queryParam("skuCode", skuCodesList).build())
					.retrieve()
					.bodyToMono(InventoryResponse[].class)
					.block();
		
		//boolean allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(inventoryResponse -> inventoryResponse.getIsInStock());
		
		boolean allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::getIsInStock); //using method reference.
		
		if(allProductsInStock) {
			orderRepository.save(order);
			
			updateQuantityForInventory(itemAndQuantity); // reduce quantity in inventory after placing orders.
			
		}else {
			throw new IllegalArgumentException("Product is not in stock!");
		}
			
	}

	private void updateQuantityForInventory(Map<String, Integer> itemAndQuantity) {
		
	//	ResponseEntity quantityReponse = restTemplate.postForEntity("http://localhost:8082/api/inventory/updateQuantityInventory", itemAndQuantity, ResponseEntity.class);
		
		@SuppressWarnings("rawtypes")
		ResponseEntity quantityReponse = webClient.get()
											.uri("http://localhost:8082/api/inventory/updateQuantityInventory", UriBuilder -> UriBuilder.queryParam("itemAndQuantity", itemAndQuantity).build())
											.retrieve()
											.bodyToMono(ResponseEntity.class)
											.block();
		
		System.out.println(quantityReponse.getBody() + ", --, " +quantityReponse.getStatusCode());
	}

	private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
		
		OrderLineItems orderLineItems = new OrderLineItems();
		orderLineItems.setPrice(orderLineItemsDto.getPrice());
		orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
		orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
		
		return orderLineItems;
	}
	
}
