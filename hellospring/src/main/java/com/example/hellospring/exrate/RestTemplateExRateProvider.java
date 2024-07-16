package com.example.hellospring.exrate;

import java.math.BigDecimal;

import org.springframework.web.client.RestTemplate;

import com.example.hellospring.payment.ExRateProvider;

public class RestTemplateExRateProvider implements ExRateProvider {

    private static final String API_URL = "https://open.er-api.com/v6/latest/";
    private final RestTemplate restTemplate;

    public RestTemplateExRateProvider(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public BigDecimal getExchangeRate(final String currency) {
        String url = API_URL + currency;

        return restTemplate.getForObject(url, ExRateData.class)
                .rates()
                .get("KRW");
    }

}
