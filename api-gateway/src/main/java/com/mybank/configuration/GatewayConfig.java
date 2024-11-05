package com.mybank.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("customers-route", r -> r.path("/customers/**")
                        .uri("lb://customers"))
                .route("risk-analysis-route", r -> r.path("/risk-analysis/**")
                        .uri("lb://risk-analysis"))
                .route("loan-proposals-route", r -> r.path("/loan-proposals/**")
                        .uri("lb://loan-proposals"))
                .route("loan-contracts-route", r -> r.path("/loan-contracts/**")
                        .uri("lb://loan-contracts"))
                .build();
    }
}
