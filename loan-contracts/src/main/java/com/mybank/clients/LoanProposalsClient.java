package com.mybank.clients;

import com.mybank.dtos.LoanProposalResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "LOAN-PROPOSALS", path = "/loan-proposals")
public interface LoanProposalsClient {
    @GetMapping(value = "/get-proposal/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<LoanProposalResponseDTO> getLoanProposal(@PathVariable("id") Integer id);
}
