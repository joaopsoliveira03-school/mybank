package com.mybank.services;

import com.mybank.clients.LoanProposalsClient;
import com.mybank.data.LoanContractsRepository;
import com.mybank.dtos.LoanProposalResponseDTO;
import com.mybank.exceptions.DuplicatedContractException;
import com.mybank.exceptions.LoanProposalNotFoundException;
import com.mybank.exceptions.LoanProposalServiceException;
import com.mybank.exceptions.ProposalNotValidException;
import com.mybank.models.LoanContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoanContractsService {

    @Autowired
    private LoanContractsRepository loanContractsRepository;

    @Autowired
    private LoanProposalsClient loanProposalsClient;

    public LoanContract approveLoan(Integer customerId, Integer proposalId) {
        ResponseEntity<LoanProposalResponseDTO> loanProposalResponseEntity = loanProposalsClient.getLoanProposal(proposalId);
        //if response is not OK, throw an exception
        if (!loanProposalResponseEntity.getStatusCode().is2xxSuccessful()) {
            //check if 404 status code
            if (loanProposalResponseEntity.getStatusCode().is4xxClientError()) {
                throw new LoanProposalServiceException("Loan proposal not found");
            }
            throw new LoanProposalServiceException("Error while trying to get loan proposal");
        }
        LoanProposalResponseDTO loanProposalResponseDTO = loanProposalResponseEntity.getBody();

        //check customerId and customer id from loan proposal
        if (!customerId.equals(loanProposalResponseDTO.getCustomerId())) {
            throw new LoanProposalNotFoundException("The customer id does not match the loan proposal");
        }

        //check if proposal is valid (approved=true, proposalExpirationDate >= today)
        if (!loanProposalResponseDTO.isApproved() || loanProposalResponseDTO.getProposalExpirationDate().isBefore(java.time.LocalDateTime.now())) {
            throw new ProposalNotValidException();
        }

        //check if loan contract already exists
        if (loanContractsRepository.existsByCustomerIdAndProposalId(customerId, proposalId)) {
            throw new DuplicatedContractException();
        }

        LoanContract loanContract = new LoanContract();
        loanContract.setCustomerId(loanProposalResponseDTO.getCustomerId());
        loanContract.setProposalId(loanProposalResponseDTO.getId());
        loanContract.setProposalDate(loanProposalResponseDTO.getProposalDate());
        loanContract.setProposalExpirationDate(loanProposalResponseDTO.getProposalExpirationDate());
        loanContract.setCustomerAge(loanProposalResponseDTO.getCustomerAge());
        loanContract.setCustomerFamilyMembersCount(loanProposalResponseDTO.getCustomerFamilyMembersCount());
        loanContract.setCustomerMonthlyAverageIncome(loanProposalResponseDTO.getCustomerMonthlyAverageIncome());
        loanContract.setCustomerExistingCreditsSum(loanProposalResponseDTO.getCustomerExistingCreditsSum());
        loanContract.setCustomerCurrentEffortRate(loanProposalResponseDTO.getCustomerCurrentEffortRate());
        loanContract.setLoanAmountRequested(loanProposalResponseDTO.getLoanAmountRequested());
        loanContract.setContractDate(java.time.LocalDateTime.now());


        //approve loan
        return loanContractsRepository.save(loanContract);
    }
}
