package com.mybank.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "proposals")
public class Proposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

