package com.example.hellospring.exrate;

import java.math.BigDecimal;

import com.example.hellospring.api.ApiTemplate;
import com.example.hellospring.api.ErApiExRateExtractor;
import com.example.hellospring.api.HttpClientApiExecutor;
import com.example.hellospring.payment.ExRateProvider;

public class WebApiExRateProvider implements ExRateProvider {

    private static final String API_URL = "https://open.er-api.com/v6/latest/";
    private final ApiTemplate apiTemplate = new ApiTemplate();

    @Override
    public BigDecimal getExchangeRate(final String currency) {
        String url = API_URL + currency;

        // method injection은 작성한 코드에 의해서 주입
        return apiTemplate.getExRate(url, new HttpClientApiExecutor(), new ErApiExRateExtractor());
    }

}
