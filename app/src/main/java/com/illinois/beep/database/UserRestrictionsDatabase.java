package com.illinois.beep.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Abstract class which collects all Dao classes, it also happens to be a singleton for convenience
 */
@Database(entities = {UserRestriction.class}, version = 1, exportSchema = false)
public abstract class UserRestrictionsDatabase extends RoomDatabase {

    public abstract UserRestrictionsDao userRestrictionsDao();

    private static volatile UserRestrictionsDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static UserRestrictionsDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserRestrictionsDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserRestrictionsDatabase.class, "user_restrictions_database")
                            .allowMainThreadQueries()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}