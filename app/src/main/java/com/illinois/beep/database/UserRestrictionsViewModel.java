package com.illinois.beep.database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

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
        return mRepository.getUserRestriction(personName, restriction);
    }

    public int isFavorite(String personName, String restriction){
        return mRepository.isFavorite(personName, restriction);
    }

    public List<String> getRestrictions(String personName){
        return mRepository.getRestrictions(personName);
    }

    public List<UserRestriction> getRestrictionsObjects(String personName){
        return mRepository.getRestrictionsObjects(personName);
    }

    public LiveData<List<UserRestriction>> getLiveRestrictionsObjects(String personName){
        return mRepository.getLiveRestrictionsObjects(personName);
    }

    public List<String> getAllUsers(){
        return mRepository.getAllUsers();
    }

}
