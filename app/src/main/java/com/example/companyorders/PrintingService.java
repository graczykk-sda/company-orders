package com.example.companyorders;

import java.text.DecimalFormat;
import java.util.List;

class PrintingService {

    void printProducts(List<AggregatedProduct> aggregatedProductList, int limit) {
        System.out.println("Product           Total Quantity          Currency          Value");
        DecimalFormat df = new DecimalFormat("##.00");
        aggregatedProductList.stream()
                .limit(limit)
                .forEach(p -> System.out.printf("%-17s %-23s %-17s %s %n",
                        p.productName(),
                        p.aggregation().quantity(),
                        p.aggregation().currency(),
                        df.format(p.aggregation().value())));
    }

}
