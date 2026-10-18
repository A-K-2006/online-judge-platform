package com.onlinejudge.submission.service;

import com.onlinejudge.submission.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubmissionService {
    SubmissionResponse submit(String userId, SubmissionRequest request);
    SubmissionResponse getById(String id);
    Page<SubmissionResponse> getByUser(String userId, Pageable pageable);
    void updateResult(String submissionId, String status, String output, Integer runtime, Integer memory);
}