package com.explore.micro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.explore.micro.model.Products;

@Repository
public interface ProductRepository extends JpaRepository<Products, Double>{

}
