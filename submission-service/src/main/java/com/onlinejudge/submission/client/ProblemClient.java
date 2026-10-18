package com.onlinejudge.submission.client;

import com.onlinejudge.common.dto.ApiResponse;
import com.onlinejudge.submission.dto.ProblemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "problem-service", url = "${services.problem.url:http://localhost:8083}")
public interface ProblemClient {
    @GetMapping("/api/v1/problems/{id}")
    ApiResponse<ProblemDto> getProblem(@PathVariable("id") Long id);
}