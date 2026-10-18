package com.onlinejudge.submission.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "submissions", schema = "submissions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private Long problemId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String code;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private String status;

    private String output;
    private Integer runtime;
    private Integer memory;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) this.status = "PENDING";
    }
}