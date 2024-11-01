package com.mybank.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MissingDataException extends RuntimeException {
    private String missingData;

    @Override
    public String getMessage() {
        return String.format("(CC) (RiskAnalysisService-MissingData): Missing data in customer record: %s", missingData);
    }
}
