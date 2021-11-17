package com.illinois.beep.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Abstract class which collects all Dao classes
 */
@Database(entities = {UserRestriction.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserRestrictionsDao userRestrictionsDao();
}
