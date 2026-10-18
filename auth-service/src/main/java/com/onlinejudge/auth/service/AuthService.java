package com.onlinejudge.auth.service;

import com.onlinejudge.auth.dto.*;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse refreshToken(String refreshToken);
}