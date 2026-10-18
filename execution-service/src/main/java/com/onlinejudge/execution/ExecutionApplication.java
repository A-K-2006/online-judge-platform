package com.onlinejudge.execution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.onlinejudge.execution", "com.onlinejudge.common"})
public class ExecutionApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExecutionApplication.class, args);
    }
}