package com.illinois.beep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.illinois.beep.database.Product;
import com.illinois.beep.database.ProductDatabase;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ProductDatabaseTest {
    @Test
    public void getElementFromDatabase() throws IOException {
        ProductDatabase.loadData(new FileInputStream("src/main/assets/products.json"));
        Product product = ProductDatabase.get("84358272");
        assertNotNull(product);
        assertEquals(product.getName(), "LA LECHERA Sweetened Condensed Milk 14 oz.");
    }

}
