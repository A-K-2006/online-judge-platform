package com.onlinejudge.submission.repository;

import com.onlinejudge.submission.entity.Submission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<Submission, String> {
    Page<Submission> findByUserId(String userId, Pageable pageable);
    Page<Submission> findByProblemId(Long problemId, Pageable pageable);
    Page<Submission> findByUserIdAndProblemId(String userId, Long problemId, Pageable pageable);
}