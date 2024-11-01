package com.mybank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGateway {

    static Logger logger = LoggerFactory.getLogger(ApiGateway.class);

    public static void main( String[] args ) {
        logger.info("ApiGateway Started!");
        SpringApplication.run(ApiGateway.class, args);
    }
}
