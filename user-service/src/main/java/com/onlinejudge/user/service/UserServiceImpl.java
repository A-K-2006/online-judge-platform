package com.onlinejudge.user.service;

import com.onlinejudge.common.exception.ResourceNotFoundException;
import com.onlinejudge.user.dto.*;
import com.onlinejudge.user.entity.UserProfile;
import com.onlinejudge.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserProfileRepository repository;

    @Override
    public UserProfileDto getProfile(String userId) {
        UserProfile profile = repository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found"));
        return toDto(profile);
    }

    @Override
    public UserProfileDto updateProfile(String userId, UpdateProfileRequest request) {
        UserProfile profile = repository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found"));
        if (request.getBio() != null) profile.setBio(request.getBio());
        if (request.getAvatarUrl() != null) profile.setAvatarUrl(request.getAvatarUrl());
        if (request.getCountry() != null) profile.setCountry(request.getCountry());
        return toDto(repository.save(profile));
    }

    @Override
    public UserProfileDto createProfile(String userId) {
        UserProfile profile = UserProfile.builder().userId(userId).build();
        return toDto(repository.save(profile));
    }

    private UserProfileDto toDto(UserProfile p) {
        return UserProfileDto.builder()
                .userId(p.getUserId()).bio(p.getBio()).avatarUrl(p.getAvatarUrl())
                .country(p.getCountry()).totalSolved(p.getTotalSolved())
                .easySolved(p.getEasySolved()).mediumSolved(p.getMediumSolved())
                .hardSolved(p.getHardSolved()).ranking(p.getRanking()).build();
    }
}