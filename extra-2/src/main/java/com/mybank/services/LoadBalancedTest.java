package com.mybank.services;

import com.mybank.dtos.EffortRateRequestDTO;
import com.mybank.dtos.EffortRateResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LoadBalancedTest {

    @Autowired
    private RestTemplate restTemplate;

    public void eurekaTest2() {
        // Variables
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<EffortRateRequestDTO> requestEntity;
        EffortRateRequestDTO requestDTO;

        // Prepare request DTO
        headers.setContentType(MediaType.APPLICATION_JSON);
        requestDTO = new EffortRateRequestDTO(1, 1000, 2000);
        requestEntity = new HttpEntity<>(requestDTO, headers);

        System.out.println("Connecting to one instance:");

        // Using the service name directly in the URI
        EffortRateResponseDTO responseDTO = restTemplate.postForObject(
                "http://RISK-ANALYSIS/risk-analysis/current-effort-rate/1",
                requestEntity,
                EffortRateResponseDTO.class
        );

        System.out.println("Effort Rate is: " + responseDTO.getEffortRate());
    }

}
