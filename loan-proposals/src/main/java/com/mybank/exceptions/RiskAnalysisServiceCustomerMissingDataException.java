package com.mybank.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RiskAnalysisServiceCustomerMissingDataException extends RuntimeException {
    private final String message;

    @Override
    public String getMessage() {
        return String.format("(CC) (LoanProposalsService-RiskAnalysisService-CustomerMissingData): RiskAnalysis service replied: %s", message);
    }
}
