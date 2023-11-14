package com.example.companyorders;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AggregateProductsService {

    private final ExchangeRatesService exchangeRatesService;

    AggregateProductsService(ExchangeRatesService exchangeRatesService) {
        this.exchangeRatesService = exchangeRatesService;
    }

    Map<String, QuantityValueAggregation> aggregateProducts(Stream<Product> products) {
        return products.collect(Collectors.groupingBy(Product::getName,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                this::calculateProductValue
                        )
                )
        );
    }

    List<AggregatedProduct> convertFromQuantityAggregationMap(Map<String, QuantityValueAggregation> aggregationMap, boolean sortDesc) {
        Stream<AggregatedProduct> aggregatedProductStream = aggregationMap.entrySet().stream()
                .map(e -> new AggregatedProduct(e.getKey(), e.getValue()));
        if (sortDesc) {
            return aggregatedProductStream
                    .sorted(Comparator.comparing((AggregatedProduct p) -> p.aggregation().value()).reversed())
                    .toList();
        } else {
            return aggregatedProductStream.toList();
        }
    }

    private QuantityValueAggregation calculateProductValue(List<Product> products) {
        int quantity = products.stream().mapToInt(Product::getQuantity).sum();
        BigDecimal value = products.stream()
                .map(this::calculatePrize)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new QuantityValueAggregation(quantity, value, Currency.getInstance("CHF"));
    }

    private BigDecimal calculatePrize(Product product) {
        return calculatePrizeInCHF(product).multiply(BigDecimal.valueOf(product.getQuantity()));
    }

    private BigDecimal calculatePrizeInCHF(Product product) {
        return exchangeRatesService.toCHF(product.getPrize(), product.getCurrency());
    }

}
