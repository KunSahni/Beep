package com.illinois.beep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.illinois.beep.database.Product;
import com.illinois.beep.database.ProductDatabase;

import org.junit.Test;

import java.io.IOException;

public class ProductDatabaseTest {
    @Test
    public void getElementFromDatabase() throws IOException {
        ProductDatabase.loadData();
        Product product = ProductDatabase.get("4c7ff1cd-496c-4df3-9e20-5758c366d853");
        assertNotNull(product);
        assertEquals(product.getName(), "Great Value Fat Free Evaporated Milk 12 oz");
    }

}
