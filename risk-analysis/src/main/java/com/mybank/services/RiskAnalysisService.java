package com.mybank.services;

import com.mybank.dtos.CustomerDTO;
import com.mybank.exceptions.CustomerServiceUnexistingCustomerException;
import com.mybank.exceptions.CustomerServiceUnexpectedException;
import com.mybank.exceptions.MissingDataException;
import com.mybank.models.EffortRate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class RiskAnalysisService {

    @Value("${endpoints.customers-microservice.baseUrl}")
    private String addressUrl;

    public EffortRate getEffortRate(
            Integer customerId,
            float monthlyAverageIncome,
            float existingCreditsSum,
            float loanAmountRequested) throws MissingDataException, CustomerServiceUnexistingCustomerException, CustomerServiceUnexpectedException {

        CustomerDTO customerDTO;
        EffortRate effortRate;
        float effortRateValue;

        customerDTO = getCustomer(customerId);

        if (customerDTO.getFamilyMembersCount() == null) {
            throw new MissingDataException("Family members count is missing");
        }

        effortRateValue = getEffortRate(
                customerDTO.getAge(),
                customerDTO.getFamilyMembersCount(),
                monthlyAverageIncome,
                existingCreditsSum,
                loanAmountRequested
        );

        effortRate = new EffortRate();
        effortRate.setCustomerId(customerDTO.getId());
        effortRate.setName(customerDTO.getName());
        effortRate.setAddress(customerDTO.getAddress());
        effortRate.setAge(customerDTO.getAge());
        effortRate.setFamilyMembersCount(customerDTO.getFamilyMembersCount());
        effortRate.setMonthlyAverageIncome(monthlyAverageIncome);
        effortRate.setExistingCreditsSum(existingCreditsSum);
        effortRate.setEffortRate(effortRateValue);
        effortRate.setDateTime(LocalDateTime.now());
        effortRate.setLoanAmountRequested(loanAmountRequested);

        return effortRate;
    }

    private CustomerDTO getCustomer(Integer customerId) throws CustomerServiceUnexistingCustomerException, MissingDataException, CustomerServiceUnexpectedException {
        /**
         * HttpStatus (expects)
         * 200 OK - Request processed successfully
         * 400 Bad Request - Undefined error
         * 404 Not Found - Customer not found
         */

        CustomerDTO customerDTO;
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<CustomerDTO> response = restTemplate.getForEntity(addressUrl + "/{customerId}", CustomerDTO.class, customerId);
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new MissingDataException(e.getResponseBodyAsString());
            } else if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new CustomerServiceUnexistingCustomerException(e.getResponseBodyAsString());
            } else {
                throw new CustomerServiceUnexpectedException(e.getResponseBodyAsString());
            }
        }
    }

    private float getEffortRate(Integer age, Integer familyMembersCount, float monthlyAverageIncome, float existingCreditsSum, float loanAmountRequested) {
        return 0.15f * familyMembersCount;
    }
}
