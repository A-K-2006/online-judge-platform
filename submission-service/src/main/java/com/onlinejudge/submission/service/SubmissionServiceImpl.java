package com.onlinejudge.submission.service;

import com.onlinejudge.common.exception.ResourceNotFoundException;
import com.onlinejudge.submission.dto.*;
import com.onlinejudge.submission.entity.Submission;
import com.onlinejudge.submission.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import static com.onlinejudge.common.constants.QueueConstants.EXECUTION_REQUEST_QUEUE;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository repository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public SubmissionResponse submit(String userId, SubmissionRequest request) {
        Submission submission = Submission.builder()
                .userId(userId)
                .problemId(request.getProblemId())
                .code(request.getCode())
                .language(request.getLanguage())
                .status("PENDING")
                .build();
        submission = repository.save(submission);

        // Publish to Redis queue for execution-service to pick up
        ExecutionRequest execReq = ExecutionRequest.builder()
                .submissionId(submission.getId())
                .code(submission.getCode())
                .language(submission.getLanguage())
                .problemId(submission.getProblemId())
                .build();
        redisTemplate.convertAndSend(EXECUTION_REQUEST_QUEUE, execReq);
        log.info("Published execution request for submission {}", submission.getId());

        return toResponse(submission);
    }

    @Override
    public SubmissionResponse getById(String id) {
        return toResponse(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Submission not found")));
    }

    @Override
    public Page<SubmissionResponse> getByUser(String userId, Pageable pageable) {
        return repository.findByUserId(userId, pageable).map(this::toResponse);
    }

    @Override
    public void updateResult(String submissionId, String status, String output, Integer runtime, Integer memory) {
        Submission sub = repository.findById(submissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Submission not found"));
        sub.setStatus(status);
        sub.setOutput(output);
        sub.setRuntime(runtime);
        sub.setMemory(memory);
        repository.save(sub);
        log.info("Updated submission {} with status {}", submissionId, status);
    }

    private SubmissionResponse toResponse(Submission s) {
        return SubmissionResponse.builder()
                .id(s.getId()).userId(s.getUserId()).problemId(s.getProblemId())
                .language(s.getLanguage()).status(s.getStatus()).output(s.getOutput())
                .runtime(s.getRuntime()).memory(s.getMemory()).createdAt(s.getCreatedAt()).build();
    }
}