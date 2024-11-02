package com.mybank.exceptions;

public class HighEffortRateException extends RuntimeException {
    @Override
    public String getMessage() {
        return "(CC) (LoanProposalsService-HighEffortRateException) Effort rate is too high";
    }
}
