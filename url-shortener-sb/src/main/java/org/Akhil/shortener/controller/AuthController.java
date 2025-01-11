package org.Akhil.shortener.controller;

import org.Akhil.shortener.config.JwtAuthenticationResponse;
import org.Akhil.shortener.dto.LoginRequest;
import org.Akhil.shortener.dto.RegisterRequest;
import org.Akhil.shortener.dto.Response;
import org.Akhil.shortener.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/public/register")
    public ResponseEntity<Response> registerUser(@RequestBody RegisterRequest registerRequest){
        authService.registerUser(registerRequest);
        Response response=new Response("Success", LocalDateTime.now(),"User registered successfully",null);
        return ResponseEntity.status(CREATED).body(response);
    }

    @PostMapping("/public/login")
    public ResponseEntity<Response> loginUser(@RequestBody LoginRequest loginrequest){
        JwtAuthenticationResponse jwtAuthenticationResponse=authService.authenticateUser(loginrequest);
        Response response=new Response("Success", LocalDateTime.now(),jwtAuthenticationResponse,null);
        return ResponseEntity.ok(response);
    }
}