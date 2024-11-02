package com.mybank.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class InconsistentDataException extends RuntimeException {
    private String message;
    @Override
    public String getMessage() {
        return "(CC) (LoanProposalsService-InconsistentDataException) Inconsistent data in the request: " + message;
    }
}
