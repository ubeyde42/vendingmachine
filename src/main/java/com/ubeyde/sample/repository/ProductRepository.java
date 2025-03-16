package com.ubeyde.sample.repository;


import com.ubeyde.sample.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
