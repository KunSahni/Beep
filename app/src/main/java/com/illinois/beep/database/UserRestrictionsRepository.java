package com.illinois.beep.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

    UserRestriction getUserRestriction(String personName, String restriction) throws ExecutionException, InterruptedException {
        Callable<UserRestriction> callable = new Callable<UserRestriction>() {
            @Override
            public UserRestriction call() throws Exception {
                return db.userRestrictionsDao().getUserRestriction(personName, restriction);
            }
        };

        Future<UserRestriction> future = Executors.newSingleThreadExecutor().submit(callable);

        return future.get();
    }

    int isFavorite(String personName, String restriction) throws ExecutionException, InterruptedException {

        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return db.userRestrictionsDao().isFavorite(personName, restriction);
            }
        };

        Future<Integer> future = Executors.newSingleThreadExecutor().submit(callable);

        return future.get();
    }

    List<String> getRestrictions(String personName) throws ExecutionException, InterruptedException {

        Callable<List<String>> callable = new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                return db.userRestrictionsDao().getRestrictions(personName);
            }
        };

        Future<List<String>> future = Executors.newSingleThreadExecutor().submit(callable);

        return future.get();
    }

    List<UserRestriction> getRestrictionsObjects(String personName) throws ExecutionException, InterruptedException {

        Callable<List<UserRestriction>> callable = new Callable<List<UserRestriction>>() {
            @Override
            public List<UserRestriction> call() throws Exception {
                return db.userRestrictionsDao().getRestrictionsObjects(personName);
            }
        };

        Future<List<UserRestriction>> future = Executors.newSingleThreadExecutor().submit(callable);

        return future.get();
    }

    LiveData<List<UserRestriction>> getUserRestrictions(String personName) {
        return db.userRestrictionsDao().getUserRestrictions(personName);
    }

    List<String> getAllUsers() throws ExecutionException, InterruptedException {

        Callable<List<String>> callable = new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                return db.userRestrictionsDao().getAllUsers();
            }
        };

        Future<List<String>> future = Executors.newSingleThreadExecutor().submit(callable);

        return future.get();
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

    void delete(String personName) {
        UserRestrictionsDatabase.databaseWriteExecutor.execute(() -> {
            mUserRestrictionsDao.delete(personName);
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
