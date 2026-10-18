package com.onlinejudge.submission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {"com.onlinejudge.submission", "com.onlinejudge.common"})
public class SubmissionApplication {
    public static void main(String[] args) {
        SpringApplication.run(SubmissionApplication.class, args);
    }
}