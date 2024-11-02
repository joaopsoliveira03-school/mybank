package com.mybank.exceptions;

public class DuplicatedContractException extends RuntimeException {
    @Override
    public String getMessage() {
        return "(CC) (LoanContractsService-DuplicatedContractException) There cannot be two contracts";
    }
}
