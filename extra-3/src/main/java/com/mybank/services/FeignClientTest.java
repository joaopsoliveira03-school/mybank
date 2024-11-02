package com.mybank.services;

import com.mybank.clients.CustomerClient;
import com.mybank.clients.RiskAnalysisClient;
import com.mybank.dtos.CustomerDTO;
import com.mybank.dtos.EffortRateRequestDTO;
import com.mybank.dtos.EffortRateResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeignClientTest {

    @Autowired
    private CustomerClient customerClient;

    @Autowired
    private RiskAnalysisClient riskAnalysisClient;

    public void test3a() {
        // Variables
        ResponseEntity<CustomerDTO> customerDTOResponseEntity;
        CustomerDTO customerDTO;

        // Make the request
        customerDTOResponseEntity = customerClient.getCustomerById(1);

        // Process the response
        if (customerDTOResponseEntity.getStatusCode() == HttpStatus.OK) {
            customerDTO = customerDTOResponseEntity.getBody();
            System.out.println("Name of Customer 1 is: " + customerDTO.getName());
        } else {
            System.out.println("Error: " + customerDTOResponseEntity.getStatusCode());
        }
    }

    public void test3b() {
        // Variables
        ResponseEntity<List<CustomerDTO>> customerDTOResponseEntity;
        List<CustomerDTO> customerDTOList;

        // Make the request
        customerDTOResponseEntity = customerClient.getAll();

        // Process the response
        if (customerDTOResponseEntity.getStatusCode() == HttpStatus.OK) {
            customerDTOList = customerDTOResponseEntity.getBody();
            System.out.println("Customer List...");
            for (CustomerDTO customer : customerDTOList) {
                System.out.println(customer.getName());
            }
        } else {
            System.out.println("Error: " + customerDTOResponseEntity.getStatusCode());
        }
    }

    public void test4() {
        // Variables
        EffortRateRequestDTO requestDTO;
        EffortRateResponseDTO responseDTO;

        // Prepare request DTO
        requestDTO = new EffortRateRequestDTO(1, 1000, 2000);

        // Get the response
        responseDTO = riskAnalysisClient.getEffortRate(1, requestDTO).getBody();

        // Print the effort rate
        System.out.println("Effort Rate is: " + responseDTO.getEffortRate());
    }
}
