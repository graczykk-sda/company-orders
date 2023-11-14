package com.example.companyorders;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

class PrintingServiceTest {

    @Test
    void shouldPrintAggregatedProducts(){
        PrintingService printingService = new PrintingService();
        List<AggregatedProduct> aggregatedProducts = aggregatedProducts();
        printingService.printProducts(aggregatedProducts, 10);
    }

    @Test
    void shouldPrintAggregatedProductsLimit4(){
        PrintingService printingService = new PrintingService();
        List<AggregatedProduct> aggregatedProducts = aggregatedProducts();
        printingService.printProducts(aggregatedProducts, 4);
    }

    List<AggregatedProduct> aggregatedProducts() {
        return List.of(
                new AggregatedProduct("Red Shoes", new QuantityValueAggregation(100, BigDecimal.valueOf(100))),
                new AggregatedProduct("Blue Shoes", new QuantityValueAggregation(200, BigDecimal.valueOf(99))),
                new AggregatedProduct("Gold Shoes", new QuantityValueAggregation(300, BigDecimal.valueOf(1002))),
                new AggregatedProduct("Black Shoes", new QuantityValueAggregation(150, BigDecimal.valueOf(10))),
                new AggregatedProduct("White Shoes", new QuantityValueAggregation(107, BigDecimal.valueOf(386))),
                new AggregatedProduct("Yellow Shoes", new QuantityValueAggregation(166, BigDecimal.valueOf(987)))
        );
    }
}