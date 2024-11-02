package com.mybank.data;

import com.mybank.models.LoanProposal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanProposalsRepository extends JpaRepository<LoanProposal, Integer> {
    boolean existsByCustomerIdAndPending(Integer customerId, boolean pending);
}
