package com.mybank.clients;

import com.mybank.dtos.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "CustomerClient", url = "http://localhost:8091/")
public interface CustomerClient {

    @GetMapping("/customers/{id}")
    ResponseEntity<CustomerDTO> getCustomerById(@PathVariable("id") Integer id);

    @GetMapping("/customers")
    ResponseEntity<List<CustomerDTO>> getAll();
}

