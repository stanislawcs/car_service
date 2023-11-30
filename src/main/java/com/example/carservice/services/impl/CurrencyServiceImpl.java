package com.example.carservice.services.impl;

import com.example.carservice.dto.converter.Response;
import com.example.carservice.services.CurrencyService;
import com.example.carservice.services.FeignCurrency;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Profile("prod")
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = {"currencyRate"})
@EnableScheduling
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {
    private final FeignCurrency feignCurrency;


    @Override
    @Cacheable
    public Response getCurrencyRate() {
        log.info("put to cache");
        return feignCurrency.getRate("", "BYN");
    }


    @CacheEvict(allEntries = true)
    @Scheduled(fixedRate = 30000)
    public void cacheEvict() {
        log.info("Cache evict");
    }


}
