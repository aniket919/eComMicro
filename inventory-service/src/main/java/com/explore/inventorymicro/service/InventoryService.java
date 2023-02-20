package com.explore.inventorymicro.service;

import java.security.KeyStore.Entry;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.explore.inventorymicro.dto.InventoryResponse;
import com.explore.inventorymicro.model.Inventory;
import com.explore.inventorymicro.repository.InventoryRepository;

@Service
public class InventoryService {

	@Autowired
	private InventoryRepository invRepo;
	
	
	@Transactional(readOnly = true)
	public List<InventoryResponse> isInStock(List<String> skuCode) {
		
		return invRepo.findBySkuCodeIn(skuCode).stream()
												.map(inventory -> 
												
														InventoryResponse.builder()
														.skuCode(inventory.getSkuCode())
														.isInStock(inventory.getQuantity() > 0)
														.build()
														).toList();
		
	}


	public Inventory updateInventory(Inventory inv) {
		
		return invRepo.saveAndFlush(inv);
	}


	@Transactional
	public Boolean updateQuantity(Map<String, Integer> itemAndQuantity) {
		
		boolean status = false;
		
		itemAndQuantity.forEach( (k,v) -> System.out.println("item name = " + k +", quantity = " + v));
		
			try {
				for(Map.Entry<String, Integer> entry : itemAndQuantity.entrySet()) {
					
					String skuCode = entry.getKey();
					Integer qty = entry.getValue();
					
					Inventory toBeupdated = invRepo.findBySkuCode(skuCode);
					
					toBeupdated.setQuantity(toBeupdated.getQuantity() - qty);
					invRepo.saveAndFlush(toBeupdated);
					
					status = true;
				}
			}catch (Exception e) {
				
				e.printStackTrace();
				status = false;
			}
		
		return status;
		
	}


}
