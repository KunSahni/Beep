package com.illinois.beep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.illinois.beep.database.Product;
import com.illinois.beep.database.ProductDatabase;
import com.illinois.beep.database.RestrictionDatabase;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class RestrictionDatabaseTest {
    @Test
    public void getRestrictionsFromDatabase() throws IOException {
        RestrictionDatabase.loadData(new FileInputStream("src/main/assets/restrictions.json"));
        List<String> restrictions = RestrictionDatabase.getRestrictions();
        assertNotNull(restrictions);
        assertEquals(restrictions.size(), 12);
        System.out.println(restrictions);
    }

}
