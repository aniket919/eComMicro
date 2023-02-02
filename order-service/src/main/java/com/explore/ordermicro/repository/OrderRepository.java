package com.explore.ordermicro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.explore.ordermicro.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{

}
