package com.onlinejudge.problem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProblemRequest {
    @NotBlank private String title;
    @NotBlank private String slug;
    @NotBlank private String description;
    @NotBlank private String difficulty;
    private String examples;
    private String constraints;
    private String tags;
}