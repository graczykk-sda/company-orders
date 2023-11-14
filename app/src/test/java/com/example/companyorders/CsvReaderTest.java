package com.example.companyorders;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvReaderTest {

    @Test
    void shouldReadAndParseCsvFile() throws FileNotFoundException {
        List<Product> actualProducts = CsvReader.readProducts("src/test/resources/products_example.csv");
        assertEquals(4, actualProducts.size());
    }

    @Test
    void shouldThrowFileNotFoundException() {
        assertThrows(FileNotFoundException.class, () -> CsvReader.readProducts("non existing file"));
    }
}