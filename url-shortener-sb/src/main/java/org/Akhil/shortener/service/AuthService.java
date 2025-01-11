package org.Akhil.shortener.service;

import org.Akhil.shortener.config.JwtAuthenticationResponse;
import org.Akhil.shortener.dto.LoginRequest;
import org.Akhil.shortener.dto.RegisterRequest;
import org.Akhil.shortener.model.User;

public interface AuthService {
    void registerUser(RegisterRequest registerRequest);
    JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest);
    User findByEmail(String email);
}