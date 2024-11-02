package com.mybank.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LoanProposalResponseDTO {
    private Integer id;

    private Integer customerId;

    private LocalDateTime proposalDate;
    private LocalDateTime proposalExpirationDate;

    private Integer customerAge;
    private Integer customerFamilyMembersCount;

    private float customerMonthlyAverageIncome;
    private float customerExistingCreditsSum;
    private float customerCurrentEffortRate;

    private float loanAmountRequested;

    private boolean pending;
    private boolean approved;
}