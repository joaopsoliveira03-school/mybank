package com.mybank.controllers;

import com.mybank.dtos.ProposalRequestDTO;
import com.mybank.models.LoanProposal;
import com.mybank.services.LoanProposalsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/loan-proposals")
public class LoanProposalsController {

    @Autowired
    private LoanProposalsService loanProposalsService;

    private ModelMapper modelMapper;

    public LoanProposalsController() {
        this.modelMapper = new ModelMapper();
    }

    @GetMapping("/landing")
    public Mono<String> landing() {
        return Mono.just("Loan proposals service is working fine...");
    }

    @PostMapping("/loan-approval-request")
    public Mono<LoanProposal> loanApprovalRequest(@RequestBody ProposalRequestDTO proposalRequestDTO) {
        LoanProposal loanProposal = modelMapper.map(proposalRequestDTO, LoanProposal.class);
        return Mono.fromSupplier(() -> {
            LoanProposal newLoanProposal = loanProposalsService.add(loanProposal);
            return newLoanProposal;
        });
    }

    @GetMapping("/get-proposal/{id}")
    public Mono<LoanProposal> getProposal(@PathVariable("id") Integer id) {
        return Mono.fromSupplier(() -> loanProposalsService.get(id));
    }

    @GetMapping("/get-all-proposals")
    public Flux<LoanProposal> getAllProposals() {
        return Flux.fromIterable(loanProposalsService.get());
    }

    @PostMapping("/loan-approve")
    public Mono<Void> approveLoan(@RequestBody Map<String, Integer> request) {
        Integer customerId = request.get("customerId");
        Integer proposalId = request.get("proposalId");
        return Mono.fromRunnable(() -> loanProposalsService.approveProposal(customerId, proposalId));
    }
}
