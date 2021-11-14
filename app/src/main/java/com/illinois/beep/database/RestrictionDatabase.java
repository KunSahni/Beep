package com.illinois.beep.database;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class RestrictionDatabase {
    final static String sourcePath = "src/main/assets/restrictions.json";

    static List<String> restrictions;

    static public void loadData(InputStream data) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        restrictions = objectMapper.readValue(data,
                new TypeReference<List<String>>(){});
    }

    public static List<String> getRestrictions() {
        return restrictions;
    }
}
