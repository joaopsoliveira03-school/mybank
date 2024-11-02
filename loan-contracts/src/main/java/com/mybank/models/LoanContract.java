package com.mybank.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "loan-contracts")
public class LoanContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer customerId;
    private Integer proposalId;

    private LocalDateTime proposalDate;
    private LocalDateTime proposalExpirationDate;

    private Integer customerAge;
    private Integer customerFamilyMembersCount;

    private float customerMonthlyAverageIncome;
    private float customerExistingCreditsSum;
    private float customerCurrentEffortRate;

    private float loanAmountRequested;

    @CreatedDate
    @Column(name = "contract_date", updatable = false)
    private LocalDateTime contractDate;

}
