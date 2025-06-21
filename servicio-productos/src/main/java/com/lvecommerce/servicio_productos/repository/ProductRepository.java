package com.lvecommerce.servicio_productos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lvecommerce.servicio_productos.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
