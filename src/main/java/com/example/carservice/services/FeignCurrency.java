package com.example.carservice.services;

import com.example.carservice.dto.converter.Response;
import com.example.carservice.services.impl.FeignCurrencyFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "currencyRate",
        url = "${api.url}",
        fallback = FeignCurrencyFallBack.class)
public interface FeignCurrency {

    @GetMapping
    Response getRate(@RequestParam("first-currency") String firstCurrency,
                     @RequestParam("second-currency") String secondCurrency);

}
