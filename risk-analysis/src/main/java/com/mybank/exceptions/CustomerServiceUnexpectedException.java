package com.mybank.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomerServiceUnexpectedException extends RuntimeException {
    private String receivedMessage;

    @Override
    public String getMessage() {
        return String.format("(CC) (RiskAnalysisService-CustomerService-UnexpectedException): Customer service replied %s", receivedMessage);
    }
}
