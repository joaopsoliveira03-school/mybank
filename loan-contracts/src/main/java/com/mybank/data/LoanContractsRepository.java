package com.mybank.data;

import com.mybank.models.LoanContract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanContractsRepository extends JpaRepository<LoanContract, Integer> {
    boolean existsByCustomerIdAndProposalId(Integer customerId, Integer proposalId);
}
