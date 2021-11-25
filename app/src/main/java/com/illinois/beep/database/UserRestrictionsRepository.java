package com.illinois.beep.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class UserRestrictionsRepository {

    private UserRestrictionsDao mUserRestrictionsDao;
    private LiveData<List<UserRestriction>> mAllRestriction;
    private UserRestrictionsDatabase db;

    UserRestrictionsRepository(Application application) {
        db = UserRestrictionsDatabase.getDatabase(application);
        mUserRestrictionsDao = db.userRestrictionsDao();
        mAllRestriction = mUserRestrictionsDao.getAll();
    }

    //Read operations

    LiveData<List<UserRestriction>> getAllRestrictions() {
        return mAllRestriction;
    }

    UserRestriction getUserRestriction(String personName, String restriction) {
        return db.userRestrictionsDao().getUserRestriction(personName, restriction);
    }

    int isFavorite(String personName, String restriction) {
        return db.userRestrictionsDao().isFavorite(personName, restriction);
    }

    List<String> getRestrictions(String personName) {
        return db.userRestrictionsDao().getRestrictions(personName);
    }

    List<UserRestriction> getRestrictionsObjects(String personName) {
        return db.userRestrictionsDao().getRestrictionsObjects(personName);
    }

    LiveData<List<UserRestriction>> getLiveRestrictionsObjects(String personName) {
        return db.userRestrictionsDao().getLiveRestrictionsObjects(personName);
    }

    List<String> getAllUsers() {
        return db.userRestrictionsDao().getAllUsers();
    }

    //Create, update and delete operations

    void insert(UserRestriction userRestriction) {
        UserRestrictionsDatabase.databaseWriteExecutor.execute(() -> {
            mUserRestrictionsDao.insertOne(userRestriction);
        });
    }

    void delete(UserRestriction userRestriction) {
        UserRestrictionsDatabase.databaseWriteExecutor.execute(() -> {
            mUserRestrictionsDao.delete(userRestriction);
        });
    }

    void delete(String personName, String restriction) {
        UserRestrictionsDatabase.databaseWriteExecutor.execute(() -> {
            mUserRestrictionsDao.delete(personName, restriction);
        });
    }

    void favorite(String personName, String restriction) {
        UserRestrictionsDatabase.databaseWriteExecutor.execute(() -> {
            mUserRestrictionsDao.favorite(personName, restriction);
        });
    }

    void unfavorite(String personName, String restriction) {
        UserRestrictionsDatabase.databaseWriteExecutor.execute(() -> {
            mUserRestrictionsDao.unfavorite(personName, restriction);
        });
    }

}
