package com.mybank.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class EffortRateResponseDTO {
    private LocalDateTime dateTime;
    private Integer id;
    private int age;
    private Integer familyMembersCount;
    private float monthlyAverageIncome;
    private float existingCreditsSum;
    private float effortRate;
}