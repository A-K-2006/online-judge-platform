package com.onlinejudge.user.service;

import com.onlinejudge.user.dto.*;

public interface UserService {
    UserProfileDto getProfile(String userId);
    UserProfileDto updateProfile(String userId, UpdateProfileRequest request);
    UserProfileDto createProfile(String userId);
}