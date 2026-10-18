package com.onlinejudge.problem.repository;

import com.onlinejudge.problem.entity.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    Optional<Problem> findBySlug(String slug);
    Page<Problem> findByDifficulty(String difficulty, Pageable pageable);
    Page<Problem> findByTagsContaining(String tag, Pageable pageable);
}