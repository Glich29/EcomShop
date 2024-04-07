package org.sbislava.ecomshop.repository;

import org.sbislava.ecomshop.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
