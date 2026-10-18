package com.onlinejudge.problem.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "problems", schema = "problems")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String difficulty; // EASY, MEDIUM, HARD

    @Column(columnDefinition = "TEXT")
    private String examples;

    @Column(columnDefinition = "TEXT")
    private String constraints;

    private String tags;

    @Column(nullable = false)
    private int acceptedCount;

    @Column(nullable = false)
    private int submissionCount;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}