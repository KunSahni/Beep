package com.illinois.beep.database;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ProductDatabase {
    final static String sourcePath = "src/main/assets/products.json";

    static Map<String, Product> db;

    static public void loadData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        db = objectMapper.readValue(new File(sourcePath), new TypeReference<Map<String, Product>>(){});
    }

    public static Map<String, Product> getDb() {
        return db;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Product get(String uuid) {
        return ProductDatabase.getDb().getOrDefault(uuid, null);
    }
}
