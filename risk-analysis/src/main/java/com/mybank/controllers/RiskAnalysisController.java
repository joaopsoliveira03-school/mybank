package com.mybank.controllers;

import com.mybank.dtos.EffortRateDTO;
import com.mybank.exceptions.CustomerServiceUnexistingCustomerException;
import com.mybank.exceptions.CustomerServiceUnexpectedException;
import com.mybank.exceptions.MissingDataException;
import com.mybank.models.EffortRate;
import com.mybank.services.RiskAnalysisService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/risk-analysis")
public class RiskAnalysisController {

    @Autowired
    private RiskAnalysisService riskAnalysisService;

    private ModelMapper modelMapper;

    public RiskAnalysisController() {
        this.modelMapper = new ModelMapper();
    }

    @GetMapping("/landing")
    public ResponseEntity<String> landing() {
        return ResponseEntity.ok("Risk Analysis Service is running");
    }

    @PostMapping("/current-effort-rate/{customerId}")
    public ResponseEntity<?> currentEffortRate(@PathVariable Integer customerId, @RequestBody EffortRateDTO effortRateDTO) {
        /**
         * HttpStatus (produces)
         * 200 OK - Request processed successfully
         * 400 BAD REQUEST - Unexpected error
         * 404 NOT FOUND - Customer not found
         * 409 CONFLICT - Customer ID does not match the request body ID
         * 412 PRECONDITION FAILED - Customer has missing data (getFamilyMembersCount
         */

        EffortRate effortRate;

        if (effortRateDTO.getCustomerId() != customerId) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("(CC) (RiskAnalysisService-currentEffortRate): ID inconsistency");
        }

        try {
            effortRate = riskAnalysisService.getEffortRate(
                    customerId,
                    effortRateDTO.getMonthlyAverageIncome(),
                    effortRateDTO.getExistingCreditsSum()
            );
        } catch (MissingDataException e) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(e.getMessage());
        } catch (CustomerServiceUnexistingCustomerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (CustomerServiceUnexpectedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return new ResponseEntity<>(modelMapper.map(effortRate, EffortRateDTO.class), HttpStatus.OK);
    }
}
