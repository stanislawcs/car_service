package com.example.carservice.services.impl;

import com.example.carservice.dto.converter.Response;
import com.example.carservice.services.CurrencyService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("dev")
@Service
public class MockCurrencyServiceImpl implements CurrencyService {
    @Override
    public Response getCurrencyRate() {
        Response response = new Response();
        response.setOfficialRate(3.1);
        return response;
    }
}
