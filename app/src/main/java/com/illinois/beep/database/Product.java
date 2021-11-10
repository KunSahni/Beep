package com.illinois.beep.database;

import java.util.List;
import java.util.Map;

public class Product {
    String uuid;
    String name;
    String description;

    Map<String, String> nutrition_facts;

    List<String> ingredients;

    List<String> substitutes;

    String image_url;

    Map<String, Boolean> indications;

    public Product() {
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, String> getNutrition_facts() {
        return nutrition_facts;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<String> getSubstitutes() {
        return substitutes;
    }

    public String getImage_url() {
        return image_url;
    }

    public Map<String, Boolean> getIndications() {
        return indications;
    }
}
