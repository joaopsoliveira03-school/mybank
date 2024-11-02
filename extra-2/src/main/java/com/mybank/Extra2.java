package com.mybank;


import com.mybank.services.LoadBalancedTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Extra2 {

    static Logger logger = LoggerFactory.getLogger(Extra2.class);

    public static void main( String[] args ) {
        logger.info("Extra2 Started!");
        SpringApplication.run(Extra2.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            LoadBalancedTest loadBalancedTest = ctx.getBean(LoadBalancedTest.class);
            loadBalancedTest.eurekaTest2();
        };
    }
}
