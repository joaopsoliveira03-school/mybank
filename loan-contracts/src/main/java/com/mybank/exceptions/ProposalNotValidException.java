package com.mybank.exceptions;

public class ProposalNotValidException extends RuntimeException {
    @Override
    public String getMessage() {
        return "(CC) (LoanContractsService-ProposalNotValidException) Proposal not valid!";
    }
}
