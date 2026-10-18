package com.onlinejudge.user.dto;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String bio;
    private String avatarUrl;
    private String country;
}