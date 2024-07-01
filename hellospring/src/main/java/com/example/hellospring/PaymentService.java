package com.example.hellospring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PaymentService {

    private static final String EXCHANGE_RATE_URL = "https://open.er-api.com/v6/latest/";

    public Payment prepare(final Long orderId, final String currency, final BigDecimal foreignCurrencyAmount)
            throws IOException {
        // TODO: 환율 가져오기
        BigDecimal exRate = getExchangeRate(currency);

        // TODO: 금액 계산
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);

        // TODO: 유효 시간 계산
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);
        return new Payment(orderId, currency, foreignCurrencyAmount,
                exRate, convertedAmount, validUntil);
    }

    private BigDecimal getExchangeRate(final String currency) throws IOException {
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
