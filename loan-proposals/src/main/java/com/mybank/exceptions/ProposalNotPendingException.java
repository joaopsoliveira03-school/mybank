package com.mybank.exceptions;

public class ProposalNotPendingException extends RuntimeException {
    @Override
    public String getMessage() {
        return "(CC) (LoanProposalsService-ProposalNotPendingException) LoanProposal is not pending";
    }
}
