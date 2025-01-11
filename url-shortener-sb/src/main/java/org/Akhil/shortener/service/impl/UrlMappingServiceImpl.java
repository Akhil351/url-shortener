package org.Akhil.shortener.service.impl;

import org.Akhil.shortener.dto.ClickEventDto;
import org.Akhil.shortener.dto.UrlMappingDto;
import org.Akhil.shortener.exception.ResourceNotFoundException;
import org.Akhil.shortener.model.ClickEvent;
import org.Akhil.shortener.model.UrlMapping;
import org.Akhil.shortener.model.User;
import org.Akhil.shortener.repository.ClickEventRepository;
import org.Akhil.shortener.repository.UrlMappingRepository;
import org.Akhil.shortener.service.UrlMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;


@Service
public class UrlMappingServiceImpl implements UrlMappingService {

    @Autowired
    private UrlMappingRepository urlMappingRepository;
    @Autowired
    private ClickEventRepository clickEventRepository;
    @Override
    public UrlMappingDto createShortUrl(String originalUrl, User user) {
        String shortUrl=generateShortUrl();
        UrlMapping urlMapping=new UrlMapping();
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setShortUrl(shortUrl);
        urlMapping.setUser(user);
        urlMapping.setCreatedDate(LocalDateTime.now());
        UrlMapping savedUrlMapping=urlMappingRepository.save(urlMapping);
        return this.convertToDto(savedUrlMapping);
    }

    @Override
    public List<UrlMappingDto> getUrlsByUser(User user) {
        List<UrlMapping> urlMappings=urlMappingRepository.findByUser(user);
        return urlMappings.stream().map(this::convertToDto).toList();
    }

    @Override
    public List<ClickEventDto> getClickEventsByDate(String shortUrl, LocalDateTime startDate, LocalDateTime endDate) {
        UrlMapping urlMapping=urlMappingRepository.findByShortUrl(shortUrl).orElseThrow(()->new ResourceNotFoundException("urlMapping not found"));
        List<ClickEvent> clickEvents=clickEventRepository.findByUrlMappingAndClickDateBetween(urlMapping,startDate,endDate);
        return this.convertToClickEventDto(clickEvents);
    }

    @Override
    public  List<ClickEventDto> getTotalClicksByUserAndDate(User user, LocalDate startDate, LocalDate endDate) {
        List<UrlMapping> urlMappings=urlMappingRepository.findByUser(user);
        List<ClickEvent> clickEvents=clickEventRepository.findByUrlMappingInAndClickDateBetween(urlMappings,startDate.atStartOfDay(),endDate.plusDays(1).atStartOfDay());
        return this.convertToClickEventDto(clickEvents);
    }

    @Override
    public UrlMapping getOriginalUrl(String shortUrl) {
        UrlMapping urlMapping= urlMappingRepository.findByShortUrl(shortUrl).orElseThrow(()->new ResourceNotFoundException("urlMapping not found"));
        urlMapping.setClickCount(urlMapping.getClickCount()+1);
        urlMappingRepository.save(urlMapping);
        ClickEvent clickEvent=new ClickEvent();
        clickEvent.setClickDate(LocalDateTime.now());
        clickEvent.setUrlMapping(urlMapping);
        clickEventRepository.save(clickEvent);
        return urlMapping;
    }

    private List<ClickEventDto> convertToClickEventDto(List<ClickEvent> clickEvents){
        List<ClickEventDto> clickEventDtos=new ArrayList<>();
        Map<LocalDate,Long> groupedClicks=new HashMap<>();
        for(ClickEvent clickEvent:clickEvents){
            LocalDate date=clickEvent.getClickDate().toLocalDate();
            groupedClicks.put(date,groupedClicks.getOrDefault(date,0L)+1);
        }
        for(LocalDate date: groupedClicks.keySet()){
            ClickEventDto clickEventDto=new ClickEventDto();
            clickEventDto.setClickDate(date);
            clickEventDto.setCount(groupedClicks.get(date));
            clickEventDtos.add(clickEventDto);
        }
        return clickEventDtos;

    }
    private UrlMappingDto convertToDto(UrlMapping urlMapping){
        UrlMappingDto urlMappingDto=new UrlMappingDto();
        urlMappingDto.setId(urlMapping.getId());
        urlMappingDto.setOriginalUrl(urlMapping.getOriginalUrl());
        urlMappingDto.setShortUrl(urlMapping.getShortUrl());
        urlMappingDto.setClickCount(urlMapping.getClickCount());
        urlMappingDto.setCreateDate(urlMapping.getCreatedDate());
        urlMappingDto.setUserName(urlMapping.getUser().getUserName());
        return urlMappingDto;
    }
    private String generateShortUrl(){
        String characters="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random=new Random();
        StringBuilder shortUrl=new StringBuilder(8);
        for(int i=0;i<8;i++){
            shortUrl.append(characters.charAt(random.nextInt(characters.length())));
        }
        return shortUrl.toString();
    }
}
