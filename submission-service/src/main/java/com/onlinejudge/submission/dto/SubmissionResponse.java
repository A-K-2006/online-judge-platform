package com.onlinejudge.submission.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class SubmissionResponse {
    private String id;
    private String userId;
    private Long problemId;
    private String language;
    private String status;
    private String output;
    private Integer runtime;
    private Integer memory;
    private LocalDateTime createdAt;
}