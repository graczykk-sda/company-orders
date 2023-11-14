package com.example.companyorders;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AggregateProductsServiceTest {

    @Mock
    private ExchangeRatesService exchangeRatesServiceMock;

    @Test
    public void shouldAggregateProducts() {
        BigDecimal blueShoesPrize = new BigDecimal("40.00");
        BigDecimal redShoesPrizeChf = new BigDecimal("35.11");
        BigDecimal redShoesPrizeEur = new BigDecimal("20.00");
        BigDecimal scotchWhiskeyPrize = new BigDecimal("80.00");

        when(exchangeRatesServiceMock.toCHF(blueShoesPrize, Currency.getInstance("CHF"))).thenReturn(blueShoesPrize);
        when(exchangeRatesServiceMock.toCHF(redShoesPrizeChf, Currency.getInstance("CHF"))).thenReturn(redShoesPrizeChf);
        when(exchangeRatesServiceMock.toCHF(redShoesPrizeEur, Currency.getInstance("EUR"))).thenReturn(redShoesPrizeEur.multiply(BigDecimal.valueOf(0.964660)));
        when(exchangeRatesServiceMock.toCHF(scotchWhiskeyPrize, Currency.getInstance("CHF"))).thenReturn(scotchWhiskeyPrize);

        AggregateProductsService aggregateProductsService = new AggregateProductsService(exchangeRatesServiceMock);

        List<Product> products = TestUtils.provideProducts();

        Map<String, QuantityValueAggregation> actualAgregates = aggregateProductsService.aggregateProducts(products.stream());

        Assertions.assertTrue(actualAgregates.containsKey("Blue Shoes"));
        Assertions.assertTrue(actualAgregates.containsKey("Red Shoes"));
        Assertions.assertTrue(actualAgregates.containsKey("Scotch Whisky"));

        QuantityValueAggregation blueShoes = actualAgregates.get("Blue Shoes");
        assertEquals(1000, blueShoes.quantity());
        assertEquals(0, BigDecimal.valueOf(40000).compareTo(blueShoes.value()));
        assertEquals("CHF", blueShoes.currency().getCurrencyCode());

        QuantityValueAggregation redShoes = actualAgregates.get("Red Shoes");
        assertEquals(1500, redShoes.quantity());
        assertEquals(0, BigDecimal.valueOf(44756.6).compareTo(redShoes.value()));
        assertEquals("CHF", redShoes.currency().getCurrencyCode());
    }

    @Test
    void shouldConvertFromQuantityAggregationMapNoSort() {
        AggregateProductsService aggregateProductsService = new AggregateProductsService(exchangeRatesServiceMock);
        Map<String, QuantityValueAggregation> quantityValueAggregationMap = quantityValueAggregationMap();

        List<AggregatedProduct> aggregatedProducts = aggregateProductsService.convertFromQuantityAggregationMap(quantityValueAggregationMap, false);

        assertEquals(3, aggregatedProducts.size());
        assertEquals("Red Shoes", aggregatedProducts.get(0).productName());
        assertEquals("Blue Shoes", aggregatedProducts.get(1).productName());
        assertEquals("Gold Shoes", aggregatedProducts.get(2).productName());
    }

    @Test
    void shouldConvertFromQuantityAggregationMapSorted() {
        AggregateProductsService aggregateProductsService = new AggregateProductsService(exchangeRatesServiceMock);
        Map<String, QuantityValueAggregation> quantityValueAggregationMap = quantityValueAggregationMap();

        List<AggregatedProduct> aggregatedProducts = aggregateProductsService.convertFromQuantityAggregationMap(quantityValueAggregationMap, true);

        assertEquals(3, aggregatedProducts.size());
        assertEquals("Blue Shoes", aggregatedProducts.get(0).productName());
        assertEquals("Gold Shoes", aggregatedProducts.get(1).productName());
        assertEquals("Red Shoes", aggregatedProducts.get(2).productName());
    }

    private Map<String, QuantityValueAggregation> quantityValueAggregationMap() {
        Map<String, QuantityValueAggregation> quantityValueAggregationMap = new LinkedHashMap<>();
        quantityValueAggregationMap.put("Red Shoes", new QuantityValueAggregation(100, BigDecimal.valueOf(100)));
        quantityValueAggregationMap.put("Blue Shoes", new QuantityValueAggregation(200, BigDecimal.valueOf(200)));
        quantityValueAggregationMap.put("Gold Shoes", new QuantityValueAggregation(50, BigDecimal.valueOf(130)));
        return quantityValueAggregationMap;
    }
}