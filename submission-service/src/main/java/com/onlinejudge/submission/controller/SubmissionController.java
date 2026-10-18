package com.onlinejudge.submission.controller;

import com.onlinejudge.common.dto.ApiResponse;
import com.onlinejudge.submission.dto.*;
import com.onlinejudge.submission.service.SubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    @PostMapping
    public ResponseEntity<ApiResponse<SubmissionResponse>> submit(Authentication auth,
                                                                  @Valid @RequestBody SubmissionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(submissionService.submit(auth.getName(), request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SubmissionResponse>> getById(@PathVariable("id") String id) {
        return ResponseEntity.ok(ApiResponse.success(submissionService.getById(id)));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<Page<SubmissionResponse>>> getMySubmissions(Authentication auth, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(submissionService.getByUser(auth.getName(), pageable)));
    }
}