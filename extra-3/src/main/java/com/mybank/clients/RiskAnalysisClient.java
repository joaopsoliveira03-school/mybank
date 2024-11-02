package com.mybank.clients;

import com.mybank.dtos.EffortRateRequestDTO;
import com.mybank.dtos.EffortRateResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "RISK-ANALYSIS", path = "/risk-analysis")
public interface RiskAnalysisClient {

    @PostMapping(value = "/current-effort-rate/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EffortRateResponseDTO> getEffortRate(
            @PathVariable("id") Integer id,
            @RequestBody EffortRateRequestDTO effortRateRequestDTO
    );
}

