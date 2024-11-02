package com.mybank;

import com.mybank.services.FeignClientTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class Extra3 {

    static Logger logger = LoggerFactory.getLogger(Extra3.class);

    public static void main( String[] args ) {
        logger.info("Extra3 Started!");
        SpringApplication.run(Extra3.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            FeignClientTest feignClientTest = ctx.getBean(FeignClientTest.class);
            feignClientTest.test3a();
            feignClientTest.test3b();
            feignClientTest.test4();
        };
    }
}
