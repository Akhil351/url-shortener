package org.Akhil.shortener.service;

import org.Akhil.shortener.config.JwtAuthenticationResponse;
import org.Akhil.shortener.dto.LoginRequest;
import org.Akhil.shortener.dto.RegisterRequest;

public interface AuthService {
    void registerUser(RegisterRequest registerRequest);
    JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest);
}