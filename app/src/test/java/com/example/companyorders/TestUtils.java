package com.example.companyorders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

class TestUtils {

    static List<Product> provideProducts() {
        return List.of(
                new Product(LocalDate.of(2008, 1, 1), 1000, "Blue Shoes", new BigDecimal("40.00"), Currency.getInstance("CHF")),
                new Product(LocalDate.of(2008, 1, 1), 1000, "Red Shoes", new BigDecimal("35.11"), Currency.getInstance("CHF")),
                new Product(LocalDate.of(2008, 1, 2), 500, "Red Shoes", new BigDecimal("20.00"), Currency.getInstance("EUR")),
                new Product(LocalDate.of(2008, 1, 5), 80, "Scotch Whisky", new BigDecimal("80.00"), Currency.getInstance("CHF"))
        );
    }
}
