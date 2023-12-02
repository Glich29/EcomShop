package org.sbislava.ecomshop.repository;

import org.sbislava.ecomshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
