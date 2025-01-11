package org.Akhil.shortener.service.impl;

import org.Akhil.shortener.dto.UrlMappingDto;
import org.Akhil.shortener.model.UrlMapping;
import org.Akhil.shortener.model.User;
import org.Akhil.shortener.repository.UrlMappingRepository;
import org.Akhil.shortener.service.UrlMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UrlMappingServiceImpl implements UrlMappingService {

    @Autowired
    private UrlMappingRepository urlMappingRepository;
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
