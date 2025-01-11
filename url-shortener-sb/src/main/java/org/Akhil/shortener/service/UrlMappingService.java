package org.Akhil.shortener.service;

import org.Akhil.shortener.dto.UrlMappingDto;
import org.Akhil.shortener.model.User;

public interface UrlMappingService {
    UrlMappingDto createShortUrl(String originalUrl, User user);
}
