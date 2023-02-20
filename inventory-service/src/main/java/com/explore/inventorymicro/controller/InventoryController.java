package com.explore.inventorymicro.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.explore.inventorymicro.dto.InventoryResponse;
import com.explore.inventorymicro.model.Inventory;
import com.explore.inventorymicro.service.InventoryService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/inventory")
@Slf4j
public class InventoryController {

	@Autowired
	private InventoryService invService;
	
	@GetMapping("/checkStock")
	@ResponseStatus(HttpStatus.OK)
	public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
		
		return invService.isInStock(skuCode);
	}
	
	@PostMapping("/updateInventory")
	public ResponseEntity<Inventory> updateInventory(@RequestBody Inventory inv){
		
		Inventory i = invService.updateInventory(inv);
		
		log.info("Inventory updated for product : " + i.getSkuCode());
		
		return new ResponseEntity<Inventory>(i, HttpStatus.CREATED);
	}
	
	@PostMapping("/updateQuantityInventory")
	public ResponseEntity<String> updateQuantity(@RequestParam Map<String, Integer> itemAndQuantity) {
		
		Boolean isQtyUpdated = invService.updateQuantity(itemAndQuantity);
		
		if(isQtyUpdated) {
			return new ResponseEntity<String>("Quantity Updated", HttpStatus.OK);
		}else
			return new ResponseEntity<String>("Error while Updating quantity", HttpStatus.NOT_MODIFIED);
		
	}
}
