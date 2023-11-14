package com.example.companyorders;

import java.math.BigDecimal;
import java.util.Currency;

public record QuantityValueAggregation(
        int quantity,
        BigDecimal value,
        Currency currency
) {
    QuantityValueAggregation(int quantity, BigDecimal value) {
        this(quantity, value, Currency.getInstance("CHF"));
    }
}
