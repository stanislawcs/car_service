package com.example.carservice.services;

import com.example.carservice.dto.converter.Response;

import java.io.IOException;

public interface CurrencyService {
    Response getCurrencyRate() throws IOException;
}
