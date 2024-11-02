package com.mybank.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class EffortRateRequestDTO {
    private Integer customerId;
    private float monthlyAverageIncome;
    private float existingCreditsSum;
}
