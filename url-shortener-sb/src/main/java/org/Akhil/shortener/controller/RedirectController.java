package org.Akhil.shortener.controller;

import org.Akhil.shortener.model.UrlMapping;
import org.Akhil.shortener.service.UrlMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedirectController {
    @Autowired
    private UrlMappingService urlMappingService;
    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirect(@PathVariable String shortUrl){
        UrlMapping urlMapping=urlMappingService.getOriginalUrl(shortUrl);
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("Location",urlMapping.getOriginalUrl());
        return ResponseEntity.status(302).headers(httpHeaders).build();
    }
}
