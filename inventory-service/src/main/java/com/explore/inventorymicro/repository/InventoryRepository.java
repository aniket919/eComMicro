package com.explore.inventorymicro.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.explore.inventorymicro.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Integer>{

//	Optional<Inventory> findBySkuCode(String skuCode);

	List<Inventory> findBySkuCodeIn(List<String> skuCode);
	
	Inventory findBySkuCode(String skuCode);

}
