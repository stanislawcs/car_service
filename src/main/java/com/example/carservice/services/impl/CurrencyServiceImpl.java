package com.example.carservice.services.impl;

import com.example.carservice.dto.converter.Response;
import com.example.carservice.services.CurrencyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Profile("prod")
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = {"currencyRate"})
@EnableScheduling
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${api.url}")
    private String url;

    @Override
    @Cacheable
    public Response getCurrencyRate() throws IOException {
        log.info("put to cache");
        String response = restTemplate.getForObject(url, String.class);
        return objectMapper.readValue(response, Response.class);
    }


    @CacheEvict(allEntries = true)
    @Scheduled(fixedRate = 30000)
    public void cacheEvict() {
        log.info("Cache evict");
    }


}
