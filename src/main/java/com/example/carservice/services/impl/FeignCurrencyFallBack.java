package com.example.carservice.services.impl;

import com.example.carservice.dto.converter.Response;
import com.example.carservice.services.FeignCurrency;
import org.springframework.stereotype.Component;

@Component
public class FeignCurrencyFallBack implements FeignCurrency {

    @Override
    public Response getRate(String firstCurrency, String secondCurrency) {
        return null;
    }

}
