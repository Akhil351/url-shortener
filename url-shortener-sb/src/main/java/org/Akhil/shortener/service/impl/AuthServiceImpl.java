package org.Akhil.shortener.service.impl;

import org.Akhil.shortener.dto.RegisterRequest;
import org.Akhil.shortener.exception.EmailAlreadyExistException;
import org.Akhil.shortener.model.User;
import org.Akhil.shortener.repository.UserRepository;
import org.Akhil.shortener.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void registerUser(RegisterRequest registerRequest) {
        userRepository.findByEmail(registerRequest.getEmail()).ifPresent((user)->{
            throw new EmailAlreadyExistException(String.format("Email: %s already exist", registerRequest.getEmail()));
        });
        User user=new User(registerRequest.getEmail(),registerRequest.getUserName(),passwordEncoder.encode(registerRequest.getPassword()),"ROLE_USER");
        userRepository.save(user);
    }
}