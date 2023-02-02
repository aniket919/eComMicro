package com.explore.ordermicro.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="order_line_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItems {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "sku_code")
	private String skuCode;
	
	@Column(name = "order_price")
	private BigDecimal price;
	
	@Column(name = "order_quantity")
	private Integer quantity;
}
