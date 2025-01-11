package org.Akhil.shortener.exception;

import org.Akhil.shortener.dto.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<Response> handleEmailAlreadyExistException(EmailAlreadyExistException ex){
        Response response=new Response("Failed", LocalDateTime.now(),null,ex.getMessage());
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Response> handleUsernameNotFoundException(BadCredentialsException ex){
        Response response=new Response("Failed", LocalDateTime.now(),null,"invalid credentials");
        return ResponseEntity.status(UNAUTHORIZED).body(response);
    }
}