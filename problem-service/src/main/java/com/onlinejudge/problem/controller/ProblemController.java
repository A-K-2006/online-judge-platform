package com.onlinejudge.problem.controller;

import com.onlinejudge.common.dto.ApiResponse;
import com.onlinejudge.problem.dto.*;
import com.onlinejudge.problem.service.ProblemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/problems")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProblemDto>> create(@Valid @RequestBody ProblemRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(problemService.create(req)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProblemDto>>> getAll(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(problemService.getAll(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProblemDto>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(problemService.getById(id)));
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<ApiResponse<ProblemDto>> getBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(ApiResponse.success(problemService.getBySlug(slug)));
    }

    @GetMapping("/difficulty/{difficulty}")
    public ResponseEntity<ApiResponse<Page<ProblemDto>>> getByDifficulty(@PathVariable String difficulty, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(problemService.getByDifficulty(difficulty, pageable)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProblemDto>> update(@PathVariable Long id, @Valid @RequestBody ProblemRequest req) {
        return ResponseEntity.ok(ApiResponse.success(problemService.update(id, req)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        problemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}