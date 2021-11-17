package com.illinois.beep.database;

import android.content.Context;

import androidx.room.Room;

public class ConcreteAppDatabase {
    private static AppDatabase db;
    private static ConcreteAppDatabase instance = null;

    private ConcreteAppDatabase(Context context) {
        db = Room.databaseBuilder(context, AppDatabase.class, "database-name").build();
    }

    public static AppDatabase getInstance(Context context){
        if(instance == null)
            instance = new ConcreteAppDatabase(context);
        return db;
    }
}