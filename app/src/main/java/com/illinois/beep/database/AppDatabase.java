package com.illinois.beep.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Abstract class which collects all Dao classes
 */
@Database(entities = {User.class, UserRestriction.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract UserRestrictionsDao userRestrictionsDao();
}
