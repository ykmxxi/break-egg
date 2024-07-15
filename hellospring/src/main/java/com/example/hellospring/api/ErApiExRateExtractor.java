package com.example.hellospring.api;

import java.math.BigDecimal;

import com.example.hellospring.exrate.ExRateData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ErApiExRateExtractor implements ExRateExtractor {

    @Override
    public BigDecimal extract(final String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ExRateData data = mapper.readValue(response, ExRateData.class);
        return data.rates().get("KRW");
    }

}
