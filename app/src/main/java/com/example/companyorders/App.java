/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.example.companyorders;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class App {

    private static final String USAGE = "Usage: orders <file1.csv> ... <fileN.csv>";
    private static final String EXCHANGE_RATES = "exchange-rates-inv.json";

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(USAGE);
            return;
        }
        Stream<Product> productStream = Arrays.stream(args)
                .map(App::readFile)
                .flatMap(Collection::stream);
        try {
            AggregateProductsService aggregateProductsService = new AggregateProductsService(new ExchangeRatesService(EXCHANGE_RATES));
            Map<String, QuantityValueAggregation> quantityValueAggregationMap = aggregateProductsService.aggregateProducts(productStream);
            List<AggregatedProduct> aggregatedProducts = aggregateProductsService.convertFromQuantityAggregationMap(quantityValueAggregationMap, true);
            PrintingService printingService = new PrintingService();
            printingService.printProducts(aggregatedProducts, 10);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Exchange rate file not readable %s", EXCHANGE_RATES), e);
        }

    }

    private static List<Product> readFile(String filename) {
        try {
            return CsvReader.readProducts(filename);
        } catch (FileNotFoundException e) {
            System.out.printf("File %s cannot be read %n, skipping...", filename);
            return List.of();
        }
    }
}