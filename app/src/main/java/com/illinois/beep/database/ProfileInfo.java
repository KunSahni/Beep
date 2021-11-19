package com.illinois.beep.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileInfo {
  public static Map<String, List<Preferance>> prefMap;

  public static void setupSampleInfo() {
    prefMap = new HashMap<>();
    addPerson("Myself");
    addPerson("Allie");
    addPerson("Jim");
    addPerson("Alex");
    addPerson("Sarah");

    // make myself lactose intolerant
    addRestriction("Myself", "lactose", true);

    // make allie vegan
    addRestriction("Allie", "vegan", false);
  }

  public static void addRestriction(String personName, String whichRestriction, boolean important) {
    assert prefMap != null;

    prefMap.get(personName).add(new Preferance(whichRestriction, important));
  }

  public static void addPerson(String personName) {
    prefMap.put(personName, new ArrayList<>());
  }
}
