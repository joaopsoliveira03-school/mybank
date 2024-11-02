package com.mybank;

import com.mybank.dtos.EffortRateRequestDTO;
import com.mybank.dtos.EffortRateResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class DiscoveryClientTest {

    @Autowired
    private DiscoveryClient discoveryClient;

    public void eurekaTest() {
        // Variables
        String serviceName = "RISK-ANALYSIS";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<EffortRateRequestDTO> requestEntity;
        EffortRateRequestDTO requestDTO;
        EffortRateResponseDTO responseDTO;

        // Prepare request DTO
        headers.setContentType(MediaType.APPLICATION_JSON);
        requestDTO = new EffortRateRequestDTO(1, 1000, 2000);
        requestEntity = new HttpEntity<>(requestDTO, headers);

        // Get active instances from Eureka Server
        System.out.println("Getting active instances of service: " + serviceName);
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);

        // Connect individually to each active instance
        for (ServiceInstance instance : instances) {
            String uri = instance.getUri().toString();
            System.out.println("Connecting to instance: " + uri);

            responseDTO = restTemplate.postForObject(
                    uri + "/risk-analysis/current-effort-rate/1",
                    requestEntity,
                    EffortRateResponseDTO.class
            );

            System.out.println("Effort Rate is: " + responseDTO.getEffortRate());
        }
    }
}
