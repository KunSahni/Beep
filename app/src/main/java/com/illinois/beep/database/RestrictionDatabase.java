package com.illinois.beep.database;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RestrictionDatabase {
    final static String sourcePath = "src/main/assets/restrictions.json";

    static List<String> restrictions;

    static public void loadData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        restrictions = objectMapper.readValue(new File(sourcePath),
                new TypeReference<List<String>>(){});
    }

    public static List<String> getRestrictions() {
        return restrictions;
    }
}
