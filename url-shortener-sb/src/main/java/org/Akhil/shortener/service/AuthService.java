package org.Akhil.shortener.service;

import org.Akhil.shortener.dto.RegisterRequest;

public interface AuthService {
    void registerUser(RegisterRequest registerRequest);
}