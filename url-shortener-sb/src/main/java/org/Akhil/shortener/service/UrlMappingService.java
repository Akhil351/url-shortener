package org.Akhil.shortener.service;

import org.Akhil.shortener.dto.ClickEventDto;
import org.Akhil.shortener.dto.UrlMappingDto;
import org.Akhil.shortener.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface UrlMappingService {
    UrlMappingDto createShortUrl(String originalUrl, User user);
    List<UrlMappingDto> getUrlsByUser(User user);
    List<ClickEventDto> getClickEventsByDate(String shortUrl, LocalDateTime startDate,LocalDateTime endDate);
    List<ClickEventDto> getTotalClicksByUserAndDate(User user, LocalDate startDate, LocalDate endDate);
}
