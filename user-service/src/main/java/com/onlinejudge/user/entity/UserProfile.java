package com.onlinejudge.user.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "profiles", schema = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserProfile {
    @Id
    private String userId;

    private String bio;
    private String avatarUrl;
    private String country;

    @Column(nullable = false)
    private int totalSolved;

    @Column(nullable = false)
    private int easySolved;

    @Column(nullable = false)
    private int mediumSolved;

    @Column(nullable = false)
    private int hardSolved;

    private int ranking;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}