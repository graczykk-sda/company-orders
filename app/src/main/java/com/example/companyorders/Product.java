package com.example.companyorders;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvNumber;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {
    @CsvBindByName(column = "Date")
    @CsvDate("d/M/yyyy")
    private LocalDate date;
    @CsvBindByName(column = "Quantity")
    private int quantity;
    @CsvBindByName(column = "Product")
    private String name;
    @CsvBindByName(column = "Price")
    @CsvNumber("#.##")
    private BigDecimal prize;
    @CsvBindByName(column = "Currency")
    private Currency currency;
}
