package com.mybank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Extra1 {

    static Logger logger = LoggerFactory.getLogger(Extra1.class);

    public static void main( String[] args ) {
        logger.info("Extra1 Started!");
        SpringApplication.run(Extra1.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            DiscoveryClientTest discoveryClientTest = ctx.getBean(DiscoveryClientTest.class);
            discoveryClientTest.eurekaTest();
        };
    }
}
