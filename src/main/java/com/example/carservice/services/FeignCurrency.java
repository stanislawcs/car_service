package com.example.carservice.services;

import com.example.carservice.dto.converter.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "currencyRate", url = "http://localhost:8080/converter/get-rate")
public interface FeignCurrency {

    @GetMapping
    Response getRate(@RequestParam("first-currency") String firstCurrency, @RequestParam("second-currency") String secondCurrency);
}
