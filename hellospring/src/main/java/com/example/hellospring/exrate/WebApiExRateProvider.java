package com.example.hellospring.exrate;

import java.math.BigDecimal;

import com.example.hellospring.api.ApiTemplate;
import com.example.hellospring.payment.ExRateProvider;

public class WebApiExRateProvider implements ExRateProvider {

    private static final String API_URL = "https://open.er-api.com/v6/latest/";
    private final ApiTemplate apiTemplate;

    public WebApiExRateProvider(final ApiTemplate apiTemplate) {
        this.apiTemplate = apiTemplate;
    }

    @Override
    public BigDecimal getExchangeRate(final String currency) {
        String url = API_URL + currency;

        // method injection은 작성한 코드에 의해서 주입
        // default callback 지정해 사용, 다른 callback 사용 시 생성해서 전달하면 된다
        return apiTemplate.getExRate(url);
    }

}
