package com.mybank.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoanProposalNotFoundException extends RuntimeException {
    private String message;
    @Override
    public String getMessage() {
        return "(CC) (LoanProposalsService-ProposalNotFoundException) LoanProposal does not exist: " + message;
    }
}
