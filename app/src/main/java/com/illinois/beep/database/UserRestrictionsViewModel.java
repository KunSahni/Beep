package com.illinois.beep.database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * This is now the access point for all data, to understand better what each query does,
 * check UserRestrictionsDao.java
 */
public class UserRestrictionsViewModel extends AndroidViewModel {

    private UserRestrictionsRepository mRepository;

    private final LiveData<List<UserRestriction>> mAllRestrictions;

    public UserRestrictionsViewModel (Application application) {
        super(application);
        mRepository = new UserRestrictionsRepository(application);
        mAllRestrictions = mRepository.getAllRestrictions();
    }

    public LiveData<List<UserRestriction>> getAllRestrictions() { return mAllRestrictions; }

    public void insert(UserRestriction userRestriction) { mRepository.insert(userRestriction); }
    public void delete(UserRestriction userRestriction) { mRepository.delete(userRestriction); }
    public void delete(String personName, String restriction){ mRepository.delete(personName, restriction); }
    public void delete(String personName){ mRepository.delete(personName); }

    public void favorite(String personName, String restriction){ mRepository.favorite(personName, restriction); }
    public void unfavorite(String personName, String restriction){ mRepository.unfavorite(personName, restriction); }

    public UserRestriction getUserRestriction(String personName, String restriction){
        try {
            return mRepository.getUserRestriction(personName, restriction);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int isFavorite(String personName, String restriction){
        try {
            return mRepository.isFavorite(personName, restriction);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<String> getRestrictions(String personName){
        try {
            return mRepository.getRestrictions(personName);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<UserRestriction> getRestrictionsObjects(String personName){
        try {
            return mRepository.getRestrictionsObjects(personName);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<List<UserRestriction>> getUserRestrictions(String personName){
        return mRepository.getUserRestrictions(personName);
    }

    public List<String> getAllUsers(){
        try {
            return mRepository.getAllUsers();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
