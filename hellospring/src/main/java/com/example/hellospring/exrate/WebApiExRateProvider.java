package com.example.hellospring.exrate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Collectors;

import com.example.hellospring.payment.ExRateProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// import org.springframework.stereotype.Component;

// @Component // configuration 파일에 설정 없이 컴포넌트 애노테이션을 사용해도 됨
public class WebApiExRateProvider implements ExRateProvider {

    private static final String EXCHANGE_RATE_URL = "https://open.er-api.com/v6/latest/";

    @Override
    public BigDecimal getExchangeRate(final String currency) {
        URI uri;
        try {
            uri = new URI(EXCHANGE_RATE_URL + currency);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        String response;
        try {
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                response = br.lines().collect(Collectors.joining());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ObjectMapper mapper = new ObjectMapper();
        ExRateData data;
        try {
            data = mapper.readValue(response, ExRateData.class);
            return data.rates().get("KRW");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
