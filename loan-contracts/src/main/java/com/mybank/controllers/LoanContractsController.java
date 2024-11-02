package com.mybank.controllers;

import com.mybank.dtos.LoanApprovalRequestDTO;
import com.mybank.models.LoanContract;
import com.mybank.services.LoanContractsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan-contracts")
public class LoanContractsController {

    @Autowired
    private LoanContractsService loanContractsService;

    @GetMapping("/landing")
    public ResponseEntity<String> landing() {
        return ResponseEntity.ok("Loan contracts service is working fine...");
    }

    @PostMapping("/loan-confirmation")
    public ResponseEntity<?> confirmLoan(@RequestBody LoanApprovalRequestDTO loanApprovalRequestDTO) {
        try {
            LoanContract loanContract = loanContractsService.approveLoan(loanApprovalRequestDTO.getCustomerId(), loanApprovalRequestDTO.getProposalId());
            return ResponseEntity.status(HttpStatus.CREATED).body(loanContract);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
