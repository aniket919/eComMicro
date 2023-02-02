package com.explore.ordermicro.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemsDto {

	private Integer id;
	private String skuCode;
	private BigDecimal price;
	private Integer quantity;
}
