package com.example.companyorders;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

final class CsvReader {

    static List<Product> readProducts(String filename) throws FileNotFoundException {
        return new CsvToBeanBuilder<Product>(new FileReader(filename))
                .withType(Product.class)
                .build()
                .parse();
    }
}
