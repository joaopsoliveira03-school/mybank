package com.mybank.services;

import com.mybank.data.CustomersRepository;
import com.mybank.exceptions.UnexistingCustomerException;
import com.mybank.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomersService {

    @Autowired
    private CustomersRepository customersRepository;

    public List<Customer> getAll() {
        return customersRepository.findAll();
    }

    public Customer get(Integer id) throws UnexistingCustomerException {
        return customersRepository.findById(id).orElseThrow(UnexistingCustomerException::new);
    }

    public Customer add(Customer customer) {
        return customersRepository.save(customer);
    }

    public Customer update(Integer id, Customer customer) throws UnexistingCustomerException {
        if (!customersRepository.existsById(id) || customer.getId() != id) {
            throw new UnexistingCustomerException();
        }
        return customersRepository.save(customer);
    }

    public void delete(Integer id) {
        if (!customersRepository.existsById(id)) {
            throw new UnexistingCustomerException();
        }
        customersRepository.deleteById(id);
    }
}
