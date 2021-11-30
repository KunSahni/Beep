package com.illinois.beep;

import java.util.HashSet;
import java.util.Set;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RestrictionViewModel extends ViewModel {

    private MutableLiveData<Set<String>> restrictions;
    public MutableLiveData<Set<String>> getRestrictions() {
        if (restrictions == null) {
            restrictions = new MutableLiveData<>();
            loadRestrictions();
        }
        return restrictions;
    }

    private void loadRestrictions() {
        // Do an asynchronous operation to fetch users.
        Set<String> restrictions = new HashSet<>();

        restrictions.add("milks");

        this.restrictions.setValue(restrictions);
    }
}
