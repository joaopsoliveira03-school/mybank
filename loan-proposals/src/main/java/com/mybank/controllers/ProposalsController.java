package com.mybank.controllers;

import com.mybank.dtos.ProposalRequestDTO;
import com.mybank.models.Proposal;
import com.mybank.services.ProposalsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/loan-proposals")
public class ProposalsController {

    @Autowired
    private ProposalsService proposalsService;

    private ModelMapper modelMapper;

    public ProposalsController() {
        this.modelMapper = new ModelMapper();
    }

    @GetMapping("/landing")
    public Mono<String> landing() {
        return Mono.just("Loan proposals service is working fine...");
    }

    @PostMapping("/loan-approval-request")
    public Mono<Proposal> loanApprovalRequest(@RequestBody ProposalRequestDTO proposalRequestDTO) {
        Proposal proposal = modelMapper.map(proposalRequestDTO, Proposal.class);
        return Mono.fromSupplier(() -> {
            Proposal newProposal = proposalsService.add(proposal);
            return newProposal;
        });
    }

    @GetMapping("/get-proposal/{id}")
    public Mono<Proposal> getProposal(@PathVariable Integer id) {
        return Mono.fromSupplier(() -> proposalsService.get(id));
    }

    @GetMapping("/get-all-proposals")
    public Flux<Proposal> getAllProposals() {
        return Flux.fromIterable(proposalsService.get());
    }
}
