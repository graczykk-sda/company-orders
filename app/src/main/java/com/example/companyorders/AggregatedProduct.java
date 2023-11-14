package com.example.companyorders;

record AggregatedProduct(
        String productName,
        QuantityValueAggregation aggregation
) {
}
