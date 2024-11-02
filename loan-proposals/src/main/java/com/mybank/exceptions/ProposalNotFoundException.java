package com.mybank.exceptions;

public class ProposalNotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return "(CC) (LoanProposalsService-ProposalNotFoundException) LoanProposal does not exist";
    }
}
