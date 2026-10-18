package com.onlinejudge.problem.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ProblemDto {
    private Long id;
    private String title;
    private String slug;
    private String description;
    private String difficulty;
    private String examples;
    private String constraints;
    private String tags;
    private int acceptedCount;
    private int submissionCount;
}