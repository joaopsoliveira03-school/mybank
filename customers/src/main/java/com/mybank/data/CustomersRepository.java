package com.mybank.data;

import com.mybank.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomersRepository extends JpaRepository<Customer, Integer> {
}
