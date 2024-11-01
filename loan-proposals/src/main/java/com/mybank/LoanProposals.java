package com.mybank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoanProposals {

    static Logger logger = LoggerFactory.getLogger(LoanProposals.class);

    public static void main( String[] args ) {
        logger.info("LoanProposals Started!");
        SpringApplication.run(LoanProposals.class, args);
    }
}
