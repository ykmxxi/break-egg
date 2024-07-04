package com.example.hellospring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WebApiExRatePaymentService extends PaymentService {

    private static final String EXCHANGE_RATE_URL = "https://open.er-api.com/v6/latest/";
    
    @Override
    BigDecimal getExchangeRate(final String currency) throws IOException {
        URL url = new URL(EXCHANGE_RATE_URL + currency);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = br.lines().collect(Collectors.joining());
        br.close();

        ObjectMapper mapper = new ObjectMapper();
        ExRateData data = mapper.readValue(response, ExRateData.class);
        return data.rates().get("KRW");
    }

}
