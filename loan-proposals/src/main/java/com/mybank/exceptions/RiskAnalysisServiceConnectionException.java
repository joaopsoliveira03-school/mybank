package com.mybank.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@AllArgsConstructor
@Getter
public class RiskAnalysisServiceConnectionException extends RuntimeException {
    private HttpStatusCode httpStatusCode;
    private String message;

    @Override
    public String getMessage() {
        return "(CC) (LoanProposalsService-RiskAnalysisService-ServiceConnectionException): " +
                "There was a problem connecting to the customers service - " +
                "HttpStatusCode: " + httpStatusCode.value() + " " +
                "Message: " + message;
    }
}