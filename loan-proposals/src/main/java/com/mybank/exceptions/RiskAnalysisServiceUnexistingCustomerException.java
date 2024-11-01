package com.mybank.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RiskAnalysisServiceUnexistingCustomerException extends RuntimeException {
    private String message;

    @Override
    public String getMessage() {
        return String.format(
            "(CC) (LoanProposalsService-RiskAnalysisService-UnexistingCustomerException) RiskAnalysis service replied: %s", message);
    }
}
