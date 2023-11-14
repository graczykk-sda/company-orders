package com.example.companyorders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExchangeRatesServiceTest {

    private ExchangeRatesService exchangeRatesService;

    @BeforeEach
    void setup() throws IOException {
        exchangeRatesService = new ExchangeRatesService("exchange-rates-inv.json");
    }

    @Test
    void shouldConvertFromEUR() {
        BigDecimal actualValue = exchangeRatesService.toCHF(BigDecimal.valueOf(100.00), Currency.getInstance("EUR"));
        assertEquals(0, BigDecimal.valueOf(96.466).compareTo(actualValue));
    }

    @Test
    void shouldConvertFromCHF() {
        BigDecimal actualValue = exchangeRatesService.toCHF(new BigDecimal("100"), Currency.getInstance("CHF"));
        assertEquals(0, BigDecimal.valueOf(100).compareTo(actualValue));
    }

    @Test
    void shouldThrowExchangeRateUnknown() {
        assertThrows(ExchangeRateUnknownException.class,
                () -> exchangeRatesService.toCHF(BigDecimal.valueOf(100.00), Currency.getInstance("SYP")));
    }

}