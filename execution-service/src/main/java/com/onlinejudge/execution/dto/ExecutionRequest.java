package com.onlinejudge.execution.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ExecutionRequest implements Serializable {
    private String submissionId;
    private String code;
    private String language;
    private Long problemId;
}