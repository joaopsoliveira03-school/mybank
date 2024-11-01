package com.mybank.services;

import com.mybank.data.ProposalsRepository;
import com.mybank.dtos.EffortRateRequestDTO;
import com.mybank.dtos.EffortRateResponseDTO;
import com.mybank.exceptions.*;
import com.mybank.models.Proposal;
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
public class ProposalsService {

    @Autowired
    private ProposalsRepository proposalsRepository;

    @Value("${endpoints.risk-analysis-microservice.baseUrl}")
    private String addressUrl;

    public Proposal add(Proposal proposal) {
        EffortRateResponseDTO effortRateResponseDTO;

        // Check for duplicated proposal
        if (proposalsRepository.existsByCustomerIdAndPending(proposal.getCustomerId(), true)) {
            throw new DuplicatedProposalException();
        }

        // Load EffortRateDTO
        effortRateResponseDTO = getEffortRate(
                proposal.getCustomerId(),
                proposal.getCustomerMonthlyAverageIncome(),
                proposal.getCustomerExistingCreditsSum()
        );

        // Fill proposal data
        proposal.setProposalDate(LocalDateTime.now());
        proposal.setProposalExpirationDate(LocalDateTime.now().plusDays(7));
        proposal.setCustomerAge(effortRateResponseDTO.getAge());
        proposal.setCustomerFamilyMembersCount(effortRateResponseDTO.getFamilyMembersCount());
        proposal.setCustomerCurrentEffortRate(effortRateResponseDTO.getEffortRate());
        proposal.setPending(true);
        proposal.setApproved(false);

        // Save the proposal
        return proposalsRepository.save(proposal);
    }

    public Proposal get(Integer id) {
        return proposalsRepository.findById(id)
                .orElseThrow(() -> new ProposalNotFoundException());
    }

    public List<Proposal> get() {
        return proposalsRepository.findAll();
    }

    private EffortRateResponseDTO getEffortRate(Integer customerId, float monthlyAverageIncome, float existingCreditsSum) {
        // Prepare request DTO
        EffortRateRequestDTO effortRateRequestDTO = new EffortRateRequestDTO(
                customerId, monthlyAverageIncome, existingCreditsSum
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

