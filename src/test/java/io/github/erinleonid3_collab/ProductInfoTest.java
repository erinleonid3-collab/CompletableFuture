package io.github.erinleonid3_collab;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductInfoTest {

    @Test
    void shouldBeEqualWhenFieldsMatch() {
        ProductInfo a = new ProductInfo("Ноутбук", 899.99, "desc", 4.7);
        ProductInfo b = new ProductInfo("Ноутбук", 899.99, "desc", 4.7);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void shouldNotBeEqualWhenPriceDiffers() {
        ProductInfo a = new ProductInfo("Ноутбук", 899.99, "desc", 4.7);
        ProductInfo b = new ProductInfo("Ноутбук", 100.0, "desc", 4.7);
        assertNotEquals(a, b);
    }

    @Test
    void toStringShouldContainAllFields() {
        ProductInfo info = new ProductInfo("Ноутбук", 899.99, "Нет данных", 4.7);
        String s = info.toString();
        assertTrue(s.contains("Ноутбук"));
        assertTrue(s.contains("899.99"));
        assertTrue(s.contains("Нет данных"));
        assertTrue(s.contains("4.7"));
    }
}