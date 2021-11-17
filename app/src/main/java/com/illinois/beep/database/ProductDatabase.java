package com.illinois.beep.database;

import android.content.res.AssetManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.illinois.beep.MainActivity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class ProductDatabase {
    final static String sourcePath = "src/main/assets/products.json";

    static Map<String, Product> db;

    static public void loadData(InputStream data) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        db = objectMapper.readValue(data, new TypeReference<Map<String, Product>>(){});
    }

    public static Map<String, Product> getDb() {
        return db;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Product get(String id) {
        return ProductDatabase.getDb().getOrDefault(id, null);
    }
}
