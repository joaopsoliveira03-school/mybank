package com.mybank.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanApprovalRequestDTO {
    private Integer customerId;
    private Integer proposalId;
}
