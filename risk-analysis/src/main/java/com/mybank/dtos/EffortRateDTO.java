package com.mybank.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EffortRateDTO {
    private LocalDateTime dateTime;
    private Integer customerId;
    private int age;
    private Integer familyMembersCount;
    private float monthlyAverageIncome;
    private float existingCreditsSum;
    private float effortRate;
}
