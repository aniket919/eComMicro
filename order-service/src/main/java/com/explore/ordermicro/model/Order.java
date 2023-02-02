package com.explore.ordermicro.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "order_number")
	private String orderNumber;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<OrderLineItems> orderLineItemsList;
}
