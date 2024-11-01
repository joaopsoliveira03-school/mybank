package com.mybank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoanContracts {

    static Logger logger = LoggerFactory.getLogger(LoanContracts.class);

    public static void main( String[] args ) {
        logger.info("EurekaServer Started!");
        SpringApplication.run(LoanContracts.class, args);
    }
}
