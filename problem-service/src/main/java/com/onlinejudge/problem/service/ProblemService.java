package com.onlinejudge.problem.service;

import com.onlinejudge.problem.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProblemService {
    ProblemDto create(ProblemRequest request);
    ProblemDto getById(Long id);
    ProblemDto getBySlug(String slug);
    Page<ProblemDto> getAll(Pageable pageable);
    Page<ProblemDto> getByDifficulty(String difficulty, Pageable pageable);
    ProblemDto update(Long id, ProblemRequest request);
    void delete(Long id);
}