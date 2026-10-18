package com.onlinejudge.user.controller;

import com.onlinejudge.common.dto.ApiResponse;
import com.onlinejudge.user.dto.*;
import com.onlinejudge.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileDto>> getMyProfile(Authentication auth) {
        return ResponseEntity.ok(ApiResponse.success(userService.getProfile(auth.getName())));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserProfileDto>> getProfile(@PathVariable String userId) {
        return ResponseEntity.ok(ApiResponse.success(userService.getProfile(userId)));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileDto>> updateProfile(Authentication auth,
                                                                     @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(ApiResponse.success(userService.updateProfile(auth.getName(), request)));
    }

    @PostMapping("/profile")
    public ResponseEntity<ApiResponse<UserProfileDto>> createProfile(Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(userService.createProfile(auth.getName())));
    }
}