package com.example.companyorders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Currency;
import java.util.Map;

class ExchangeRatesService {

    private final Map<String, Double> currencyCodeRateMap;
    ExchangeRatesService(String fileSource) throws IOException {

        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(fileSource);
        ObjectMapper objectMapper = new ObjectMapper();
        if (resourceAsStream != null) {
            this.currencyCodeRateMap = objectMapper.readValue(resourceAsStream, new TypeReference<>() {
            });
        } else {
            throw new FileNotFoundException(String.format("File cannot be read: %s", fileSource));
        }
    }
    BigDecimal toCHF(BigDecimal value, Currency currency) {
        if (currency.getCurrencyCode().equals("CHF")) {
            return value;
        }
        if (currencyCodeRateMap.containsKey(currency.getCurrencyCode())) {
            return value.multiply(BigDecimal.valueOf(currencyCodeRateMap.get(currency.getCurrencyCode())));
        } else {
            throw new ExchangeRateUnknownException(String.format("Cannot determine exchange rate for %s", currency.getCurrencyCode()));
        }
    }
}
