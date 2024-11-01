package com.mybank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Customers {

    static Logger logger = LoggerFactory.getLogger(Customers.class);

    public static void main( String[] args ) {
        logger.info("Customers Started!");
        SpringApplication.run(Customers.class, args);
    }
}
