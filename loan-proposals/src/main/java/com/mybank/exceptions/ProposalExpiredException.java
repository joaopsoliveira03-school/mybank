package com.mybank.exceptions;


public class ProposalExpiredException extends RuntimeException {

    @Override
    public String getMessage() {
        return "(CC) (LoanProposalsService-ProposalExpiredException) LoanProposal expired ";
    }
}
