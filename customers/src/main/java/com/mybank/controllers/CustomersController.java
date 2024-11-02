package com.mybank.controllers;

import com.mybank.dtos.CustomerDTO;
import com.mybank.exceptions.UnexistingCustomerException;
import com.mybank.models.Customer;
import com.mybank.services.CustomersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomersController {

    @Autowired
    private CustomersService customersService;

    private ModelMapper modelMapper;

    public CustomersController() {
        this.modelMapper = new ModelMapper();
    }

    @GetMapping("/landing")
    public ResponseEntity<String> landing() {
        return ResponseEntity.ok("Welcome to the customers API!");
    }

    @GetMapping
    ResponseEntity<List<CustomerDTO>> getAll() {
        return ResponseEntity.ok(customersService.getAll().stream().map(customer -> modelMapper.map(customer, CustomerDTO.class)).toList());
    }

    @GetMapping("/{id}")
    ResponseEntity<?> get(@PathVariable("id") Integer id) {
        /**
         * HttpStatus (produces)
         * 200 OK - Request processed successfully
         * 400 BAD REQUEST - undefined error
         * 404 NOT FOUND - Customer not found
         */
        Customer customer;

        try {
            customer = customersService.get(id);
        } catch (UnexistingCustomerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(customer, CustomerDTO.class));
    }

    @PostMapping
    ResponseEntity<?> add(@RequestBody CustomerDTO customerDTO) {
        Customer newCustomer;

        try {
            newCustomer = customersService.add(modelMapper.map(customerDTO, Customer.class));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(newCustomer, CustomerDTO.class));
    }

    @PutMapping("/{id}")
    ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody CustomerDTO customerDTO) {
        Customer updatedCustomer;

        if (customerDTO.getId() != id) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("(CC) (CustomerService-Update): ID in URL does not match ID in body!");
        }

        try {
            updatedCustomer = customersService.update(id, modelMapper.map(customerDTO, Customer.class));
        } catch (UnexistingCustomerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(updatedCustomer, CustomerDTO.class));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        try {
            customersService.delete(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
