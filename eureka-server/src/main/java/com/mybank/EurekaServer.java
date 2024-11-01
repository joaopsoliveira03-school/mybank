package com.mybank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServer {

    static Logger logger = LoggerFactory.getLogger(EurekaServer.class);

    public static void main( String[] args ) {
        logger.info("EurekaServer Started!");
        SpringApplication.run(EurekaServer.class, args);
    }
}
