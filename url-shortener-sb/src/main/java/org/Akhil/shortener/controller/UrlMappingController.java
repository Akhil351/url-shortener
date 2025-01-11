package org.Akhil.shortener.controller;

import org.Akhil.shortener.dto.ClickEventDto;
import org.Akhil.shortener.dto.Response;
import org.Akhil.shortener.dto.UrlMappingDto;
import org.Akhil.shortener.model.User;
import org.Akhil.shortener.model.UserRequestContext;
import org.Akhil.shortener.service.AuthService;
import org.Akhil.shortener.service.UrlMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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

    @GetMapping("/myUrls")
    @PreAuthorize("@roleValidate.validateRole()")
    public ResponseEntity<Response> getUserUrls(){
        User user=authService.findByEmail(context.getEmail());
        List<UrlMappingDto> urlMappingDtos=urlMappingService.getUrlsByUser(user);
        Response response=new Response("Success", LocalDateTime.now(),urlMappingDtos,null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/analytics/{shortUrl}")
    @PreAuthorize("@roleValidate.validateRole()")
    public ResponseEntity<Response> getUrlAnalytics(@PathVariable String shortUrl,@RequestParam("startDate") String startDate
    ,@RequestParam("endDate") String endDate){
        DateTimeFormatter formatter=DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime start=LocalDateTime.parse(startDate,formatter);
        LocalDateTime end=LocalDateTime.parse(endDate,formatter);
        List<ClickEventDto> clickEventDtos=urlMappingService.getClickEventsByDate(shortUrl,start,end);
        Response response=new Response("Success", LocalDateTime.now(),clickEventDtos,null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/totalClicks")
    @PreAuthorize("@roleValidate.validateRole()")
    public ResponseEntity<Response> getTotalClicksByDate(@RequestParam("startDate") String startDate
            ,@RequestParam("endDate") String endDate){
        DateTimeFormatter formatter=DateTimeFormatter.ISO_DATE;
        LocalDate start=LocalDate.parse(startDate,formatter);
        LocalDate end=LocalDate.parse(endDate,formatter);
        User user=authService.findByEmail(context.getEmail());
        List<ClickEventDto> totalClicks=urlMappingService.getTotalClicksByUserAndDate(user,start,end);
        Response response=new Response("Success", LocalDateTime.now(),totalClicks,null);
        return ResponseEntity.ok(response);

    }



}
