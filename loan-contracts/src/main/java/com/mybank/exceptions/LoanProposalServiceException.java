package com.mybank.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoanProposalServiceException extends RuntimeException {
    private String message;

    @Override
    public String getMessage() {
        return String.format(
            "(CC) (LoanProposalsService-LoanProposalServiceException) LoanProposals service replied: %s", message);
    }
}
