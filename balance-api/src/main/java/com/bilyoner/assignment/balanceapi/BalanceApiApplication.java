package com.bilyoner.assignment.balanceapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableJpaAuditing
@SpringBootApplication
public class BalanceApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(BalanceApiApplication.class, args);
    }
}
