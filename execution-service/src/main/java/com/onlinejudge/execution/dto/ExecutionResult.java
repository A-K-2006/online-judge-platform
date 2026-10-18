package com.onlinejudge.execution.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ExecutionResult implements Serializable {
    private String submissionId;
    private String status;
    private String output;
    private Integer runtime;
    private Integer memory;
}