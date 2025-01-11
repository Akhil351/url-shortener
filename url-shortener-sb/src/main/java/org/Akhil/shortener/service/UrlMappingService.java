package org.Akhil.shortener.service;

import org.Akhil.shortener.dto.UrlMappingDto;
import org.Akhil.shortener.model.User;

import java.util.List;

public interface UrlMappingService {
    UrlMappingDto createShortUrl(String originalUrl, User user);
    List<UrlMappingDto> getUrlsByUser(User user);
}
