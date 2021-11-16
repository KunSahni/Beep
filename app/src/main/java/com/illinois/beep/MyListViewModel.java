package com.illinois.beep;

import com.illinois.beep.database.Product;
import com.illinois.beep.database.ProductDatabase;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyListViewModel extends ViewModel {

    private MutableLiveData<List<MyListItem>> myList;
    public LiveData<List<MyListItem>> getMyList() {
        if (myList == null) {
            myList = new MutableLiveData<>();
            loadMyList();
        }
        return myList;
    }

    private void loadMyList() {
        // Do an asynchronous operation to fetch users.
        List<MyListItem> items = new ArrayList<>();
        for (Product product: ProductDatabase.getDb().values()) {
            items.add(new MyListItem(product, 1));
        }

        myList.postValue(items);
    }
}
