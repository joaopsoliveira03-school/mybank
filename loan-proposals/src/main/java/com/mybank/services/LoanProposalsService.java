package com.mybank.services;

import com.mybank.data.LoanProposalsRepository;
import com.mybank.dtos.EffortRateRequestDTO;
import com.mybank.dtos.EffortRateResponseDTO;
import com.mybank.exceptions.*;
import com.mybank.models.LoanProposal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoanProposalsService {

    @Autowired
    private LoanProposalsRepository loanProposalsRepository;

    @Value("${endpoints.risk-analysis-microservice.baseUrl}")
    private String addressUrl;

    public LoanProposal add(LoanProposal loanProposal) {
        EffortRateResponseDTO effortRateResponseDTO;

        // Check for duplicated loanProposal
        if (loanProposalsRepository.existsByCustomerIdAndPending(loanProposal.getCustomerId(), true)) {
            throw new DuplicatedProposalException();
        }

        // Load EffortRateDTO
        effortRateResponseDTO = getEffortRate(
                loanProposal.getCustomerId(),
                loanProposal.getCustomerMonthlyAverageIncome(),
                loanProposal.getCustomerExistingCreditsSum(),
                loanProposal.getLoanAmountRequested()
        );

        // Fill loanProposal data
        loanProposal.setProposalDate(LocalDateTime.now());
        loanProposal.setProposalExpirationDate(LocalDateTime.now().plusDays(7));
        loanProposal.setCustomerAge(effortRateResponseDTO.getAge());
        loanProposal.setCustomerFamilyMembersCount(effortRateResponseDTO.getFamilyMembersCount());
        loanProposal.setCustomerCurrentEffortRate(effortRateResponseDTO.getEffortRate());
        loanProposal.setLoanAmountRequested(effortRateResponseDTO.getLoanAmountRequested());
        loanProposal.setPending(true);
        loanProposal.setApproved(false);

        // Save the loanProposal
        return loanProposalsRepository.save(loanProposal);
    }

    public LoanProposal get(Integer id) {
        return loanProposalsRepository.findById(id)
                .orElseThrow(() -> new ProposalNotFoundException());
    }

    public List<LoanProposal> get() {
        return loanProposalsRepository.findAll();
    }

    public void approveProposal(Integer customerId, Integer proposalId) {
        LoanProposal loanProposal = loanProposalsRepository.findById(proposalId)
                .orElseThrow(() -> new ProposalNotFoundException());

        if (loanProposal.isApproved() || !loanProposal.isPending()) {
            throw new ProposalNotPendingException();
        }

        if (!loanProposal.getCustomerId().equals(customerId)) {
            throw new InconsistentDataException();
        }

        if (loanProposal.getProposalExpirationDate().isBefore(LocalDateTime.now())) {
            throw new ProposalExpiredException();
        }

        EffortRateResponseDTO effortRateResponseDTO = getEffortRate(
                loanProposal.getCustomerId(),
                loanProposal.getCustomerMonthlyAverageIncome(),
                loanProposal.getCustomerExistingCreditsSum(),
                loanProposal.getLoanAmountRequested()
        );

        if (loanProposal.getCustomerFamilyMembersCount() != effortRateResponseDTO.getFamilyMembersCount()) {
            throw new InconsistentDataException("Number of family members has changed since the loanProposal creation.");
        }

        if (effortRateResponseDTO.getEffortRate() >= 35) {
            throw new HighEffortRateException();
        }

        loanProposal.setApproved(true);
        loanProposal.setPending(false);
        loanProposalsRepository.save(loanProposal);
    }

    private EffortRateResponseDTO getEffortRate(Integer customerId, float monthlyAverageIncome, float existingCreditsSum, float loanAmountRequested) {
        // Prepare request DTO
        EffortRateRequestDTO effortRateRequestDTO = new EffortRateRequestDTO(
                customerId, monthlyAverageIncome, existingCreditsSum, loanAmountRequested
        );

        // Prepare WebClient
        WebClient webClient = WebClient.builder()
                .baseUrl(addressUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        // Send request and handle response
        Mono<EffortRateResponseDTO> mono = webClient.post()
                .uri("/" + customerId)
                .body(BodyInserters.fromValue(effortRateRequestDTO))
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError(),
                        response -> {
                            if (response.statusCode() == HttpStatus.NOT_FOUND) {
                                return response.bodyToMono(String.class)
                                        .flatMap(body -> Mono.error(new RiskAnalysisServiceUnexistingCustomerException(body)));
                            } else if (response.statusCode() == HttpStatus.PRECONDITION_FAILED) {
                                return response.bodyToMono(String.class)
                                        .flatMap(body -> Mono.error(new RiskAnalysisServiceCustomerMissingDataException(body)));
                            } else {
                                return response.bodyToMono(String.class)
                                        .flatMap(body -> Mono.error(new RiskAnalysisServiceConnectionException(response.statusCode(), body)));
                            }
                        }
                )
                .bodyToMono(EffortRateResponseDTO.class);

        // Return the response DTO
        return mono.block();
    }
}

