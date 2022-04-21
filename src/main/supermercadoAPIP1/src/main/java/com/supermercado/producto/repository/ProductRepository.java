package com.proyecto.app.repository;

import com.proyecto.app.entity.Product;
import com.proyecto.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public Product findByEmail(String name);
}