package com.onlinejudge.problem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.onlinejudge.problem", "com.onlinejudge.common"})
public class ProblemApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProblemApplication.class, args);
    }
}