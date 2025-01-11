package org.Akhil.shortener.controller;

import org.Akhil.shortener.dto.Response;
import org.Akhil.shortener.dto.UrlMappingDto;
import org.Akhil.shortener.model.User;
import org.Akhil.shortener.model.UserRequestContext;
import org.Akhil.shortener.service.AuthService;
import org.Akhil.shortener.service.UrlMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/urls")
public class UrlMappingController {
    @Autowired
    private UrlMappingService urlMappingService;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRequestContext context;

    @PostMapping("/shorten")
    @PreAuthorize("@roleValidate.validateRole()")
    public ResponseEntity<Response> createShortUrl(@RequestBody Map<String,String> request){
        String originalUrl=request.get("originalUrl");
        User user=authService.findByEmail(context.getEmail());
        UrlMappingDto urlMappingDto=urlMappingService.createShortUrl(originalUrl,user);
        Response response=new Response("Success", LocalDateTime.now(),urlMappingDto,null);
        return ResponseEntity.ok(response);
    }
}
