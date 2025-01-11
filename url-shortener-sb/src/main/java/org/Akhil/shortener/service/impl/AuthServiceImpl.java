package org.Akhil.shortener.service.impl;

import org.Akhil.shortener.config.JwtAuthenticationResponse;
import org.Akhil.shortener.config.JwtUtils;
import org.Akhil.shortener.dto.LoginRequest;
import org.Akhil.shortener.dto.RegisterRequest;
import org.Akhil.shortener.exception.EmailAlreadyExistException;
import org.Akhil.shortener.exception.ResourceNotFoundException;
import org.Akhil.shortener.model.User;
import org.Akhil.shortener.repository.UserRepository;
import org.Akhil.shortener.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Override
    public void registerUser(RegisterRequest registerRequest) {
        userRepository.findByEmail(registerRequest.getEmail()).ifPresent((user)->{
            throw new EmailAlreadyExistException(String.format("Email: %s already exist", registerRequest.getEmail()));
        });
        User user=new User(registerRequest.getEmail(),registerRequest.getUserName(),passwordEncoder.encode(registerRequest.getPassword()),"ROLE_USER");
        userRepository.save(user);
    }

    @Override
    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
        String jwtToken=jwtUtils.generateToken(userDetails);
        return new JwtAuthenticationResponse(jwtToken);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException(String.format("User with email:%s not found",email)));
    }
}