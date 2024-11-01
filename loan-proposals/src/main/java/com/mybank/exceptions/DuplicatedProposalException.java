package com.mybank.exceptions;

public class DuplicatedProposalException extends RuntimeException {
    @Override
    public String getMessage() {
        return "(CC) (LoanProposalsService-DuplicatedProposalException) There cannot be two pending proposals for the same customer";
    }
}
